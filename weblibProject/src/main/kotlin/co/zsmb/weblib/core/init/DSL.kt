package co.zsmb.weblib.core.init

import co.zsmb.koinjs.dsl.module.Module
import co.zsmb.weblib.core.Component
import co.zsmb.weblib.core.di.TheKoin
import co.zsmb.weblib.core.routing.Router

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
    private val states = mutableSetOf<State>()
    private lateinit var defaultState: State

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

    fun routing(setup: StateCollection.() -> Unit) {
        val collection = StateCollection()
        collection.setup()

        val (states, defaultState) = collection.get()
        this.states += states
        this.defaultState = defaultState
    }

    private val afterInitActions = mutableListOf<() -> Unit>()

    fun afterInit(actions: () -> Unit) {
        afterInitActions += actions
    }

    fun build() {
        // MODULES
        addDefaultModules(modules)
        TheKoin.init(modules)
        Router.init(states, defaultState)

        // COMPONENTS
        initAsync(components) {

            // AFTER INIT
            afterInitActions.forEach { it.invoke() }
        }
    }

}

data class State(val path: String, val component: Component)

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

    internal fun build(): State {
        return State(
                path ?: throw IllegalStateException("State path not inited"),
                handler ?: throw IllegalStateException("State handler not inited")
        )
    }

}

@InitDsl
class StateCollection internal constructor() {

    private lateinit var defaultState: State

    private val states = mutableSetOf<State>()

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

    internal fun get(): Pair<Set<State>, State> {
        if (!hasDefault) {
            throw IllegalStateException("No default state set in routing setup")
        }
        return Pair(states, defaultState)
    }

}
