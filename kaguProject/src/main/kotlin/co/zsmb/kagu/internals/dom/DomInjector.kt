package co.zsmb.kagu.internals.dom

import co.zsmb.kagu.core.Component
import co.zsmb.kagu.core.Controller
import co.zsmb.kagu.core.di.inject
import co.zsmb.kagu.internals.routing.Router
import co.zsmb.kagu.services.templates.TemplateLoader
import org.w3c.dom.Element
import org.w3c.dom.HTMLElement
import org.w3c.dom.Node
import kotlin.browser.document

internal object DomInjector {

    private lateinit var selectors: Set<String>
    private lateinit var compsMap: Map<String, Component>

    private val templateLoader by inject<TemplateLoader>()

    fun init(selectors: Set<String>, compsMap: Map<String, Component>) {
        DomInjector.selectors = selectors
        DomInjector.compsMap = compsMap
    }

    /**
     * Injects all components found in the subtree of [node], fetching their
     * HTML contents and creating a new controller for each of them.
     */
    fun injectComponentsAsync(node: Node) {
        node.visitSubtreeThat(
                predicate = { it.nodeName in selectors },
                action = { placeholder ->
                    val component = compsMap[placeholder.nodeName.toLowerCase()]!!

                    templateLoader.get(url = component.templateUrl, callback = { root ->
                        initRoot(component, root)
                        placeholder.replaceWith(root)
                    })
                }
        )
    }

    /**
     * Same as injectComponentsAsync, but caches the created controllers under [rootCompNode].
     */
    fun injectSubcomponentsAsyncWithCaching(rootCompNode: Node) {
        rootCompNode.visitSubtreeThat(
                predicate = { it.nodeName in selectors },
                action = { placeholder ->
                    val component = compsMap[placeholder.nodeName.toLowerCase()]!!

                    templateLoader.get(url = component.templateUrl, callback = { root ->
                        val ctrl = initRoot(component, root)
                        placeholder.replaceWith(root)
                        ComponentCache.putController(rootCompNode, ctrl)

                        // Subcomponent loaded, check if root component is currently active
                        val route = Router.getRoute()
                        val node = ComponentCache.getNode(route)
                        if (node == rootCompNode) {
                            ctrl.onAdded()
                        }
                    })
                }
        )
    }

    private fun initRoot(component: Component, root: Element): Controller {
        initLinks(root)

        val controller = component.controller()
        controller.root = root
        controller.onCreate()

        return controller
    }

    internal fun initLinks(root: Node) {
        val linkAttr = "data-kt-href"

        root.visitSubtreeThat(
                predicate = { it.nodeName == "a".toUpperCase() },
                action = { a ->
                    a as HTMLElement

                    val ref = a.getAttribute(linkAttr) ?: return@visitSubtreeThat

                    a.setAttribute("href", "#$ref")
                    a.removeAttribute("data-kt-href")
                    a.onclick = {
                        it.preventDefault()
                        Router.navigateTo(ref)
                    }
                }
        )
    }

    private val appRoot by lazy {
        val root = document.findFirstNodeThat { it.nodeName == "app-root".toUpperCase() }!!
        val newRoot = createElement("div")
        root.replaceWith(newRoot)
        newRoot
    }

    fun injectAppComponentAsync(route: String, component: Component) {
        if (route in ComponentCache) {
            injectCachedRoute(route)
            return
        }

        templateLoader.get(url = component.templateUrl, callback = { root ->
            val controller = initRoot(component, root)

            injectSubcomponentsAsyncWithCaching(root)

            ComponentCache.putNode(route, root)
            ComponentCache.putController(root, controller)

            injectCachedRoute(route)
        })
    }

    private fun injectCachedRoute(route: String) {

        appRoot.childNodes.forEach {
            val controllers = ComponentCache.getControllers(it)
            controllers.forEach(Controller::onRemoved)
        }
        appRoot.removeChildren()

        val node = ComponentCache.getNode(route)!!
        val controllers = ComponentCache.getControllers(node)

        appRoot += node

        controllers.forEach(Controller::onAdded)
    }

}
