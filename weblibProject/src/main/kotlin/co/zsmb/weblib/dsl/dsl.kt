package co.zsmb.weblib.dsl

import co.zsmb.koinjs.dsl.module.Module
import co.zsmb.weblib.core.Component
import co.zsmb.weblib.core.init
import co.zsmb.weblib.di.TheKoin
import co.zsmb.weblib.di.logging.LoggerModule
import co.zsmb.weblib.routing.Router


inline fun application(setup: SetupRoot.() -> Unit) {
    val root = SetupRoot()
    root.setup()
    root.build()
}

typealias ComponentCollection = PartCollection<Component>
typealias ModuleCollection = PartCollection<Module>

class SetupRoot {

    private val components = mutableSetOf<Component>()
    private val modules = mutableSetOf<Module>()
    private val states = mutableSetOf<State>()

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
        states += collection.get()
    }

    private val afterInitActions = mutableListOf<() -> Unit>()

    fun afterInit(actions: () -> Unit) {
        afterInitActions += actions
    }

    private fun addDefaultModules() {
        modules += LoggerModule()
    }

    fun build() {
        // MODULES
        addDefaultModules()
        TheKoin.init(modules)
        Router.init(states)

        // COMPONENTS
        init(components) {

            // AFTER INIT
            afterInitActions.forEach { it.invoke() }
        }
    }

}

data class State(val path: String, val component: Component)

class PartCollection<T> internal constructor() {

    private val parts = mutableSetOf<T>()

    operator fun T.unaryPlus() {
        parts += this
    }

    internal fun get() = parts

}

class StateBuilder internal constructor() {

    var path: String? = null
    var handler: Component? = null

    internal fun build(): State {
        return State(
                path ?: throw IllegalStateException("path not inited"),
                handler ?: throw IllegalStateException("path not inited")
        )
    }

}

class StateCollection internal constructor() {

    private val states = mutableSetOf<State>()
    private var hasDefault = false

    fun state(setup: StateBuilder.() -> Unit) {
        val stateBuilder = StateBuilder()
        stateBuilder.setup()
        states += stateBuilder.build()
    }

    fun defaultState(setup: StateBuilder.() -> Unit) {
        state(setup)
        hasDefault = true
    }

    internal fun get(): Set<State> {
        if (!hasDefault) {
            throw IllegalStateException("no default state in routing setup")
        }
        return states
    }

}
