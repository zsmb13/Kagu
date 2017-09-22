package co.zsmb.weblib.util

import org.w3c.dom.Element
import org.w3c.dom.Node
import org.w3c.dom.NodeList
import org.w3c.dom.get

inline fun NodeList.forEach(function: (Node) -> Unit) {
    for (i in 0 until this.length) {
        function(this[i]!!)
    }
}

fun NodeList.toList(): List<Node> {
    val result = mutableListOf<Node>()
    this.forEach { result.add(it) }
    return result
}

fun Node.visitSubtreeThat(predicate: (Node) -> Boolean, action: (Node) -> Unit) {
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

fun Node.findFirstNodeThat(predicate: (Node) -> Boolean): Node? {
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

fun Node.visitChildrenThat(predicate: (Node) -> Boolean, action: (Node) -> Unit) {
    this.childNodes.forEach {
        if (predicate(it)) {
            action(it)
        }
        it.visitChildrenThat(predicate, action)
    }
}

fun Node.visitDepthFirst(action: (Node) -> Unit) {
    this.childNodes.forEach {
        action(it)
        it.visitDepthFirst(action)
    }
}

fun Node.visitBreadthFirst(action: (Node) -> Unit) {
    this.childNodes.forEach(action)
    this.childNodes.forEach { it.visitBreadthFirst(action) }
}

fun Node.replaceWith(replacement: Node) {
    this as Element
    this.replaceWith(replacement)
}
