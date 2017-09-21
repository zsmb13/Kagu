package co.zsmb.weblib.routing

import co.zsmb.weblib.core.Component
import co.zsmb.weblib.dsl.State
import co.zsmb.weblib.jquery.jq
import org.w3c.dom.Element
import kotlin.browser.window

object Router {

    fun printCurentUrl() {
        println("Current location: ${JSON.stringify(window.location)}")
        println("Current location hash: ${window.location.hash}")
    }

    val states = mutableMapOf<String, Component>()

    fun getHash() = window.location.hash

    fun getRoute() = getHash().substring(1)

    fun setHash(fragment: String) {
        window.location.hash = fragment
    }

    private fun setUrl(url: String) {
        window.history.pushState("", "this is the new window title", url)
    }

    fun cleanUrl() {
        // removes "index.html", for example

        val href = window.location.href
        println("href was: $href")
        val hash = href.substringAfterLast('#', missingDelimiterValue = "").dropWhile { it == '/' }
        val base = href.substringBeforeLast('#').substringBeforeLast('/')

        val newHref = "$base/#/$hash"

        if (href != newHref) {
            setUrl(newHref)
        }
    }

    fun init(states: MutableSet<State>) {
        states.forEach {
            this.states[it.path] = it.component
        }
    }

    fun recognizesRoute() = states.containsKey(getRoute())

    fun getAppElement(): Element {
        val tempComponent = states.getOrElse(getRoute()) { throw IllegalStateException("No such component") }
        return createElement(tempComponent.selector)
    }

    private fun createElement(selector: String): Element {
        val html = "<$selector></$selector>"
        return jq.parseHTML(html)[0] as Element
    }

}
