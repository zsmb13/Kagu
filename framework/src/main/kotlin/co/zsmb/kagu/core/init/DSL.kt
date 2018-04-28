package co.zsmb.kagu.core.init

import co.zsmb.kagu.core.Component
import co.zsmb.kagu.internals.di.KaguKoin
import co.zsmb.kagu.internals.init.addDefaultModules
import co.zsmb.kagu.internals.init.initAsync
import co.zsmb.kagu.internals.routing.Router
import co.zsmb.koinjs.dsl.module.Module

@DslMarker
annotation class InitDsl

inline fun application(setup: SetupRoot.() -> Unit) {
    val root = SetupRoot()
    root.setup()
    root.build()
}

typealias ComponentCollection = PartCollection<Component>
typealias ModuleCollection = PartCollection<Module>

@InitDsl
class SetupRoot {

    private val components = mutableSetOf<Component>()
    private val modules = mutableSetOf<Module>()
    private val states = mutableSetOf<StateDefinition>()
    private lateinit var defaultState: StateDefinition

    private lateinit var routingConfig: RoutingConfig

    fun components(setup: ComponentCollection.() -> Unit) {
        val collection = ComponentCollection()
        collection.setup()
        components += collection.get()
    }

    fun modules(setup: ModuleCollection.() -> Unit) {
        val collection = ModuleCollection()
        collection.setup()
        modules += collection.get()
    }

    fun routing(setup: RoutingSetup.() -> Unit) {
        val routingSetup = RoutingSetup()
        routingSetup.setup()

        val (states, defaultState) = routingSetup.getStateDefinitions()
        this.states += states
        this.defaultState = defaultState

        this.routingConfig = routingSetup.getRoutingConfig()
    }

    private val afterInitActions = mutableListOf<() -> Unit>()

    fun afterInit(actions: () -> Unit) {
        afterInitActions += actions
    }

    fun build() {
        // MODULES
        addDefaultModules(modules)
        KaguKoin.init(modules)
        Router.init(states, defaultState, routingConfig)

        // COMPONENTS
        initAsync(components) {

            // AFTER INIT
            afterInitActions.forEach { it.invoke() }
        }
    }

}

data class StateDefinition(val path: String, val component: Component)

@InitDsl
class PartCollection<T> internal constructor() {

    private val parts = mutableSetOf<T>()

    operator fun T.unaryPlus() {
        parts += this
    }

    internal fun get() = parts

}

@InitDsl
class StateBuilder internal constructor() {

    var path: String? = null
    var handler: Component? = null

    internal fun build(): StateDefinition {
        return StateDefinition(
                path ?: throw IllegalStateException("State path not inited"),
                handler ?: throw IllegalStateException("State handler not inited")
        )
    }

}

@InitDsl
class RoutingSetup internal constructor() {

    private val routingConfig = RoutingConfig()

    private lateinit var defaultState: StateDefinition

    private val states = mutableSetOf<StateDefinition>()

    private var hasDefault = false

    fun state(setup: StateBuilder.() -> Unit) {
        val stateBuilder = StateBuilder()
        stateBuilder.setup()
        states += stateBuilder.build()
    }

    fun defaultState(setup: StateBuilder.() -> Unit) {
        if (hasDefault) {
            throw IllegalStateException("Default state is already set")
        }

        val stateBuilder = StateBuilder()
        stateBuilder.setup()

        val state = stateBuilder.build()
        defaultState = state
        states += state

        hasDefault = true
    }

    fun config(setup: RoutingConfig.() -> Unit) {
        routingConfig.setup()
    }

    internal fun getStateDefinitions(): Pair<Set<StateDefinition>, StateDefinition> {
        if (!hasDefault) {
            throw IllegalStateException("No default state set in routing setup")
        }
        return Pair(states, defaultState)
    }

    internal fun getRoutingConfig() = routingConfig

}

@InitDsl
class RoutingConfig {

    var noHashMode = false

}
