package co.zsmb.kagu.core.dom

import org.w3c.dom.HTMLElement
import org.w3c.dom.events.Event

fun HTMLElement.onMouseEnter(actions: (Event) -> Unit) {
    onmouseenter = actions
}

fun HTMLElement.onMouseLeave(actions: (Event) -> Unit) {
    onmouseleave = actions
}

fun HTMLElement.onClick(actions: (Event) -> Unit) {
    onclick = actions
}

fun HTMLElement.onDoubleClick(actions: (Event) -> Unit) {
    ondblclick = actions
}

fun HTMLElement.onFocus(actions: (Event) -> Unit) {
    onfocus = actions
}

fun HTMLElement.onKeyDown(actions: (Event) -> Unit) {
    onkeydown = actions
}

fun HTMLElement.onKeyUp(actions: (Event) -> Unit) {
    onkeyup = actions
}

fun HTMLElement.onKeyPress(actions: (Event) -> Unit) {
    onkeypress = actions
}
