package co.zsmb.kagu.internals.dom

import co.zsmb.kagu.core.Controller
import org.w3c.dom.Node

internal object ComponentCache {

    private val routeToNode = mutableMapOf<String, Node>()
    private val elementToControllers = mutableMapOf<Node, MutableList<Controller>>()

    operator fun contains(route: String) = route in routeToNode

    fun putNode(route: String, element: Node) {
        routeToNode[route] = element
    }

    fun putController(element: Node, controller: Controller) {
        val controllers = elementToControllers.getOrPut(element) { mutableListOf() }
        controllers += controller
    }

    fun getNode(route: String) = routeToNode[route]

    fun getControllers(element: Node): List<Controller> = elementToControllers.getOrPut(element) { mutableListOf() }

}
