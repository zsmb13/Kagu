package co.zsmb.weblib.core.routing

import co.zsmb.weblib.core.Component
import co.zsmb.weblib.core.InternalLogger
import co.zsmb.weblib.core.dom.DomInjector
import co.zsmb.weblib.core.init.StateDefinition
import kotlin.browser.window

internal object Router {

    internal data class State(val path: String, val regex: Regex, val params: List<String>, val component: Component) {
        constructor(stateDefinition: StateDefinition)
                : this(stateDefinition.path, stateDefinition.path.toRegex(), listOf(), stateDefinition.component)
    }

    val states = mutableListOf<State>()
    val currentStateParams = mutableMapOf<String, String>()

    lateinit var defaultState: State

    private fun getHash() = window.location.hash

    private fun getRoute(): String = getHash().substring(1)

    private fun setHash(fragment: String) {
        window.location.hash = fragment
        refresh()
    }

    fun navigateTo(route: String) {
        setHash(route)
    }

    fun refresh() {
        cleanUrl()

        val route = getRoute()

        val state = findState(route) ?:
                run {
                    setHash(defaultState.path)
                    return
                }

        val matchResult = state.regex.matchEntire(route) ?: throw IllegalStateException("State should match route")

        currentStateParams.clear()
        state.params.forEachIndexed { i, param ->
            val value = matchResult.groupValues[i + 1]
            currentStateParams[param] = value
        }

        DomInjector.injectAppComponentAsync(route, state.component)
    }

    private fun setUrl(url: String) {
        window.history.pushState("", "this is the new window title", url)
    }

    fun cleanUrl() {
        // removes "index.html", for example
        val href = window.location.href

        val hash = href
                .substringAfterLast('#', missingDelimiterValue = "")
                .trim('/')

        InternalLogger.d(this, "hash $hash")

        val base = href
                .substringBeforeLast('#')
                .dropLastWhile { it != '/' }

        InternalLogger.d(this, "base $base")

        val newHref = "$base#/$hash/"

        if (href != newHref) {
            setUrl(newHref)
        }
    }

    fun init(states: Set<StateDefinition>, defaultState: StateDefinition) {
        this.states += states.map(this::convertToState)
        this.defaultState = Router.State(defaultState)

        window.onhashchange = {
            InternalLogger.d(this, "Hash changed to ${getHash()}")
            refresh()
        }
    }

    private fun convertToState(it: StateDefinition): State {
        val params = mutableListOf<String>()

        val fixedPath =
                if (it.path.endsWith('/'))
                    it.path
                else
                    it.path + '/'

        var pathCopy = fixedPath
        while (pathCopy.contains(':')) {
            val param = pathCopy.substringAfter(':').substringBefore('/')
            params += param
            pathCopy = pathCopy.replace(":$param", "(\\w+)")
        }

        val regex = Regex(pathCopy.replace("/", "\\/"))

        return State(fixedPath, regex, params, it.component)
    }

    private fun findState(route: String): State? {
        return states.firstOrNull { route.matches(it.regex) }
    }

}
