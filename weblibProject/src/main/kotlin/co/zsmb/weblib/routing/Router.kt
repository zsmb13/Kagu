package co.zsmb.weblib.routing

import co.zsmb.weblib.core.Component
import co.zsmb.weblib.core.DomInjector
import co.zsmb.weblib.core.InternalLogger
import co.zsmb.weblib.core.Route
import co.zsmb.weblib.dsl.State
import kotlin.browser.window

object Router {

    val states = mutableMapOf<Route, Component>()
    lateinit var defaultState: State

    private fun getHash() = window.location.hash

    private fun getRoute(): Route = getHash().substring(1)

    private fun setHash(fragment: String) {
        window.location.hash = fragment
        refresh()
    }

    fun navigateTo(route: String) {
        setHash(route)
    }

    fun refresh() {
        if (!routeIsRecognized()) {
            setHash(defaultState.path)
        }

        val route = getRoute()

        DomInjector.injectAppComponentAsync(route, states[route]!!)
    }

    private fun setUrl(url: String) {
        window.history.pushState("", "this is the new window title", url)
    }

    fun cleanUrl() {
        // removes "index.html", for example

        val href = window.location.href

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
            InternalLogger.d(this, "Hash changed to ${getHash()}")
            refresh()
        }
    }

    fun routeIsRecognized() = states.containsKey(getRoute())

}
