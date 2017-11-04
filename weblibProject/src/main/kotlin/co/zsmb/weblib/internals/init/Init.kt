package co.zsmb.weblib.internals.init

import co.zsmb.koinjs.dsl.module.Module
import co.zsmb.weblib.core.Component
import co.zsmb.weblib.internals.dom.DomInjector
import co.zsmb.weblib.internals.routing.Router
import co.zsmb.weblib.services.http.HttpServiceModule
import co.zsmb.weblib.services.logging.LoggerModule
import co.zsmb.weblib.services.messaging.MessageModule
import co.zsmb.weblib.services.navigation.NavigatorModule
import co.zsmb.weblib.services.pathparams.PathParamsModule
import co.zsmb.weblib.services.storage.StorageModule
import co.zsmb.weblib.services.templates.TemplateModule
import org.w3c.dom.events.Event
import kotlin.browser.document
import kotlin.browser.window

private fun onLoad(actions: (Event) -> Unit) {
    window.onload = actions
}

internal fun addDefaultModules(modules: MutableSet<Module>) {
    modules += HttpServiceModule
    modules += LoggerModule
    modules += MessageModule
    modules += NavigatorModule
    modules += PathParamsModule
    modules += StorageModule
    modules += TemplateModule
}

internal fun initAsync(comps: Set<Component>, afterInitActions: () -> Unit) = onLoad {

    Router.cleanUrl()

    val selectors = comps
            .map(Component::selector)
            .map(String::toUpperCase)
            .toSet()
    val compsMap = comps.associateBy(Component::selector)

    DomInjector.init(selectors, compsMap)
    DomInjector.injectComponentsAsync(document)
    DomInjector.initLinks(document)

    Router.refresh()

    afterInitActions()

}
