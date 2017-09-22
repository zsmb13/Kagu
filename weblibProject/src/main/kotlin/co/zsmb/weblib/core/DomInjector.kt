package co.zsmb.weblib.core

import co.zsmb.weblib.network.fetchHtml
import co.zsmb.weblib.util.replaceWith
import co.zsmb.weblib.util.visitSubtreeThat
import org.w3c.dom.Node

object DomInjector {

    private lateinit var selectors: Set<String>
    private lateinit var compsMap: Map<String, Component>

    fun init(selectors: Set<String>, compsMap: Map<String, Component>) {
        this.selectors = selectors
        this.compsMap = compsMap
    }

    fun injectComponentsAsync(node: Node) {

        println("injecting node: ${node.nodeName}")

        node.visitSubtreeThat(
                predicate = { it.nodeName in selectors },
                action = { placeholder ->

                    println("Found node ${placeholder.nodeName}")

                    val component = compsMap[placeholder.nodeName.toLowerCase()]!!

                    fetchHtml(url = component.templateUrl, callback = { root ->
                        placeholder.replaceWith(root)

                        val controller = component.controller()
                        controller.root = root
                        controller.onInit()
                    })
                }
        )
    }

}
