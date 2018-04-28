package co.zsmb.kagu.internals.routing

import co.zsmb.kagu.core.Component
import co.zsmb.kagu.core.init.RoutingConfig
import co.zsmb.kagu.core.init.StateDefinition
import co.zsmb.kagu.internals.dom.DomInjector

internal object Router {

    internal data class State(val path: String, val regex: Regex, val params: List<String>, val component: Component) {
        constructor(stateDefinition: StateDefinition)
                : this(stateDefinition.path, stateDefinition.path.toRegex(), listOf(), stateDefinition.component)
    }

    val states = mutableListOf<State>()
    val currentStateParams = mutableMapOf<String, String>()

    lateinit var defaultState: State

    lateinit var pathHandler: PathHandler

    var noHashMode: Boolean = false

    fun cleanUrl() = pathHandler.cleanUrl()
    fun getRoute() = pathHandler.getRoute()

    fun navigateTo(route: String) {
        pathHandler.setHash(route)
    }

    fun refresh() {
        pathHandler.cleanUrl()

        val route = pathHandler.getRoute()

        val state = findState(route) ?: run {
            pathHandler.setHash(defaultState.path)
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


    fun init(states: Set<StateDefinition>, defaultState: StateDefinition, routingConfig: RoutingConfig) {
        Router.states += states.map(this::convertToState)
        Router.defaultState = State(defaultState)

        noHashMode = routingConfig.noHashMode
        pathHandler = if (noHashMode) NoHashPathHandler() else HashPathHandler()
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
            pathCopy = pathCopy.replace(":$param", "([\\w-]+)")
        }

        val regex = Regex(pathCopy.replace("/", "\\/"))

        return State(fixedPath, regex, params, it.component)
    }

    private fun findState(route: String) = states.firstOrNull { route.matches(it.regex) }

}
