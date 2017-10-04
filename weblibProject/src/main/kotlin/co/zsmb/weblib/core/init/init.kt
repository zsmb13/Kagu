package co.zsmb.weblib.core.init

import co.zsmb.koinjs.dsl.module.Module
import co.zsmb.weblib.core.Component
import co.zsmb.weblib.core.DomInjector
import co.zsmb.weblib.core.routing.Router
import co.zsmb.weblib.services.logging.LoggerModule
import co.zsmb.weblib.services.messaging.MessageModule
import co.zsmb.weblib.services.storage.StorageModule
import org.w3c.dom.events.Event
import kotlin.browser.document
import kotlin.browser.window

private fun onLoad(actions: (Event) -> Unit) {
    window.onload = actions
}

internal fun addDefaultModules(modules: MutableSet<Module>) {
    modules += LoggerModule
    modules += MessageModule
    modules += StorageModule
}

fun initAsync(comps: Set<Component>, afterInitActions: () -> Unit) = onLoad {

    Router.cleanUrl()

    val selectors = comps
            .map(Component::selector)
            .map(String::toUpperCase)
            .toSet()
    val compsMap = comps.associateBy(Component::selector)

    DomInjector.init(selectors, compsMap)
    DomInjector.injectComponentsAsync(document)

    Router.refresh()

    afterInitActions()

}
