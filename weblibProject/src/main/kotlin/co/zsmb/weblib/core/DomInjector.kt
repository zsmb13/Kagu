package co.zsmb.weblib.core

import co.zsmb.weblib.network.TemplateLoader
import co.zsmb.weblib.routing.ComponentCache
import co.zsmb.weblib.util.*
import org.w3c.dom.Node
import kotlin.browser.document

object DomInjector {

    private lateinit var selectors: Set<String>
    private lateinit var compsMap: Map<Selector, Component>

    fun init(selectors: Set<String>, compsMap: Map<Selector, Component>) {
        this.selectors = selectors
        this.compsMap = compsMap
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
                        placeholder.replaceWith(root)

                        val controller = component.controller()
                        controller.root = root
                        controller.onCreate()
                    })
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
            val controller = component.controller()
            controller.root = root
            controller.onCreate()

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
