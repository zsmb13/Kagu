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
        components += collection.parts.map { it.invoke() }
    }

    fun modules(setup: ModuleCollection.() -> Unit) {
        val collection = ModuleCollection()
        collection.setup()
        modules += collection.parts.map { it.invoke() }
    }

    fun states(setup: StateCollection.() -> Unit) {
        val collection = StateCollection()
        collection.setup()
        states += collection.states
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

data class State(val name: String, val component: () -> Component)

class StateCollection {
    internal val states = mutableSetOf<State>()
    infix fun String.with(component: () -> Component) {
        states.add(State(this, component))
    }
}

class PartCollection<T> internal constructor() {
    internal val parts = mutableSetOf<() -> T>()
    operator fun (() -> T).unaryPlus() {
        parts += this
    }
}
