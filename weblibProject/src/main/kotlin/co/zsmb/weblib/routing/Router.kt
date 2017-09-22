package co.zsmb.weblib.routing

import co.zsmb.weblib.core.Component
import co.zsmb.weblib.core.DomInjector
import co.zsmb.weblib.dsl.State
import co.zsmb.weblib.jquery.jq
import co.zsmb.weblib.util.findFirstNodeThat
import org.w3c.dom.Element
import org.w3c.dom.Node
import kotlin.browser.document
import kotlin.browser.window

object Router {

    val states = mutableMapOf<String, Component>()
    lateinit var defaultState: State

    private val appRoot by lazy {
        getAppRoot()
    }

    fun printCurentUrl() {
        println("Current location: ${JSON.stringify(window.location)}")
        println("Current location hash: ${window.location.hash}")
    }

    private fun getHash() = window.location.hash

    private fun getRoute() = getHash().substring(1)

    private fun setHash(fragment: String) {
        window.location.hash = fragment
        refresh()
    }

    fun refresh() {
        if (!recognizesRoute()) {
            setHash(defaultState.path)
        }

        println("injecting a root")

        val appElement = getAppElement()

        DomInjector.injectComponentsAsync(appElement)

        while (appRoot.hasChildNodes()) {
            appRoot.removeChild(appRoot.firstChild!!)
        }

        appRoot.appendChild(appElement)
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

    fun init(states: MutableSet<State>, defaultState: State) {
        states.forEach {
            this.states[it.path] = it.component
        }
        this.defaultState = defaultState

        window.onhashchange = {
            println(getHash())
            refresh()
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

    private fun getAppRoot(): Node {
        return document.findFirstNodeThat { it.nodeName == "app-root".toUpperCase() }!!
    }


}
