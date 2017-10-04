package co.zsmb.weblib.core.dom

import co.zsmb.weblib.core.Component
import co.zsmb.weblib.core.Controller
import co.zsmb.weblib.core.InternalLogger
import co.zsmb.weblib.core.Selector
import co.zsmb.weblib.core.network.TemplateLoader
import co.zsmb.weblib.core.routing.ComponentCache
import co.zsmb.weblib.core.routing.Router
import org.w3c.dom.Element
import org.w3c.dom.HTMLElement
import org.w3c.dom.Node
import kotlin.browser.document

internal object DomInjector {

    private lateinit var selectors: Set<String>
    private lateinit var compsMap: Map<Selector, Component>

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

                    TemplateLoader.get(url = component.templateUrl, callback = { root ->
                        initRoot(component, root)
                        placeholder.replaceWith(root)
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

    private fun initLinks(root: Element) {
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

        TemplateLoader.get(url = component.templateUrl, callback = { root ->
            val controller = initRoot(component, root)

            ComponentCache.putNode(route, root)
            ComponentCache.putController(root, controller)

            injectCachedRoute(route)
        })
    }

    private fun injectCachedRoute(route: String) {
        InternalLogger.d(this, "Gonna remove app root children (${appRoot.childNodes.length})")

        appRoot.childNodes.forEach {
            val controller = ComponentCache.getController(it)
            controller?.onRemoved()
        }
        appRoot.removeChildren()

        val node = ComponentCache.getNode(route)!!
        val ctrl = ComponentCache.getController(node)!!

        appRoot += node

        ctrl.onAdded()
    }

}