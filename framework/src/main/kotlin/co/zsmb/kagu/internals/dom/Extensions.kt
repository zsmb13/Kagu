package co.zsmb.kagu.internals.dom

import co.zsmb.kagu.internals.jquery.JQ
import org.w3c.dom.*

internal inline fun NodeList.forEach(function: (Node) -> Unit) {
    for (i in 0 until this.length) {
        function(this[i]!!)
    }
}

internal fun NodeList.toList(): List<Node> {
    val result = mutableListOf<Node>()
    this.forEach { result.add(it) }
    return result
}

internal fun Node.visitSubtreeThat(predicate: (Node) -> Boolean, action: (Node) -> Unit) {
    if (predicate(this)) {
        action(this)
    }
    this.childNodes.forEach {
        if (predicate(it)) {
            action(it)
        }
        it.visitChildrenThat(predicate, action)
    }
}

internal fun Node.findFirstNodeThat(predicate: (Node) -> Boolean): Node? {
    if (predicate(this)) return this

    this.childNodes.forEach {
        if (predicate(it)) {
            return it
        }

        val result = it.findFirstNodeThat(predicate)
        if (result != null) {
            return result
        }
    }

    return null
}

internal fun Node.visitChildrenThat(predicate: (Node) -> Boolean, action: (Node) -> Unit) {
    this.childNodes.forEach {
        if (predicate(it)) {
            action(it)
        }
        it.visitChildrenThat(predicate, action)
    }
}

internal fun Node.visitDepthFirst(action: (Node) -> Unit) {
    this.childNodes.forEach {
        action(it)
        it.visitDepthFirst(action)
    }
}

internal fun Node.visitBreadthFirst(action: (Node) -> Unit) {
    this.childNodes.forEach(action)
    this.childNodes.forEach { it.visitBreadthFirst(action) }
}

internal fun Node.replaceWith(replacement: Node) {
    this as Element

    val parent = this.parentNode ?: return
    parent.replaceChild(replacement, this)
}

internal fun Node.replaceWithAndKeepAttrs(replacement: HTMLElement) {
    this as HTMLElement
    this.attributes.forEach {
        val clonedAttr = it.cloneNode(true) as Attr
        replacement.attributes.setNamedItem(clonedAttr)
    }

    val parent = this.parentNode ?: return
    parent.replaceChild(replacement, this)
}

internal fun NamedNodeMap.forEach(action: (Attr) -> Unit) {
    for (i in 0 until length) {
        action(get(i)!!)
    }
}

internal fun Node.removeChildren() {
    while (hasChildNodes()) {
        removeChild(firstChild!!)
    }
}

internal operator fun Node.plusAssign(node: Node) {
    appendChild(node)
}

internal fun createElement(name: String): Element {
    val html = "<$name></$name>"
    return JQ.parseHTML(html)[0] as Element
}
