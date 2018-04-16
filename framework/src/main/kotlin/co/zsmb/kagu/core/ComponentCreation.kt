package co.zsmb.kagu.core

import co.zsmb.kagu.internals.dom.DomInjector
import co.zsmb.kagu.internals.dom.createElement
import org.w3c.dom.Node

fun Controller.createComponent(parent: Node, component: Component) {
    val element = createElement(component.selector)
    parent.appendChild(element)
    DomInjector.injectComponentsAsync(element)
}

fun Controller.createComponent(parent: Node, component: Component, vararg attrs: Pair<String, String>) {
    val element = createElement(component.selector)
    parent.appendChild(element)
    attrs.forEach { (name, value) ->
        element.setAttribute("data-kt-$name", value)
    }
    DomInjector.injectComponentsAsync(element)
}
