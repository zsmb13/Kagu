package co.zsmb.weblib.core.dom

import co.zsmb.weblib.core.Component
import co.zsmb.weblib.core.Controller
import co.zsmb.weblib.core.InternalLogger
import co.zsmb.weblib.core.Selector
import co.zsmb.weblib.core.di.inject
import co.zsmb.weblib.core.routing.ComponentCache
import co.zsmb.weblib.core.routing.Router
import co.zsmb.weblib.services.templates.TemplateLoader
import org.w3c.dom.Element
import org.w3c.dom.HTMLElement
import org.w3c.dom.Node
import kotlin.browser.document

internal object DomInjector {

    private lateinit var selectors: Set<String>
    private lateinit var compsMap: Map<Selector, Component>

    private val templateLoader by inject<TemplateLoader>()

    fun init(selectors: Set<String>, compsMap: Map<Selector, Component>) {
        DomInjector.selectors = selectors
        DomInjector.compsMap = compsMap
    }

    /**
     * Injects all components found in the subtree of [node], fetching their
     * HTML contents and creating a new controller for each of them.
     */
    fun injectComponentsAsync(node: Node) {

        InternalLogger.d(this, "Injecting top level node: ${node.nodeName}")

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

        InternalLogger.d(this, "Injecting top level node (with cache): ${rootCompNode.nodeName}")

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
        val linkAttr = "data-href"

        root.visitSubtreeThat(
                predicate = { it.nodeName == "a".toUpperCase() },
                action = { a ->
                    a as HTMLElement

                    val ref = a.getAttribute(linkAttr) ?: return@visitSubtreeThat

                    a.setAttribute("href", "#$ref")
                    a.removeAttribute("data-href")
                    a.onclick = {
                        it.preventDefault()
                        InternalLogger.d(this, "clicked on a link with ref $ref")
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
            InternalLogger.d(this, "Getting component from cache for route: $route")
            injectCachedRoute(route)
            return
        }

        InternalLogger.d(this, "Creating new component")

        templateLoader.get(url = component.templateUrl, callback = { root ->
            InternalLogger.d(this, "Fetched template for new component")

            val controller = initRoot(component, root)

            InternalLogger.d(this, "Injecting subcomponents")

            injectSubcomponentsAsyncWithCaching(root)

            ComponentCache.putNode(route, root)
            ComponentCache.putController(root, controller)

            InternalLogger.d(this, "Done caching, injecting route")

            injectCachedRoute(route)
        })
    }

    private fun injectCachedRoute(route: String) {
        InternalLogger.d(this, "Gonna remove app root children (${appRoot.childNodes.length})")

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
