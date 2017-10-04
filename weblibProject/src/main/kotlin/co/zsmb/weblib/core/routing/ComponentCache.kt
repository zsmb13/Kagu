package co.zsmb.weblib.core.routing

import co.zsmb.weblib.core.Controller
import org.w3c.dom.Node

internal object ComponentCache {

    private val routeToNode = mutableMapOf<String, Node>()
    private val elementToController = mutableMapOf<Node, Controller>()

    operator fun contains(route: String) = route in routeToNode

    fun putNode(route: String, element: Node) {
        routeToNode[route] = element
    }

    fun putController(element: Node, controller: Controller) {
        elementToController[element] = controller
    }

    fun getNode(route: String) = routeToNode[route]

    fun getController(element: Node) = elementToController[element]

}
