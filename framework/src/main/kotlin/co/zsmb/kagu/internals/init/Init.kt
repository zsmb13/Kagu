package co.zsmb.kagu.internals.init

import co.zsmb.kagu.core.Component
import co.zsmb.kagu.internals.dom.DomInjector
import co.zsmb.kagu.internals.routing.Router
import co.zsmb.kagu.services.http.HttpServiceModule
import co.zsmb.kagu.services.logging.LoggerModule
import co.zsmb.kagu.services.messaging.MessageModule
import co.zsmb.kagu.services.navigation.NavigatorModule
import co.zsmb.kagu.services.pathparams.PathParamsModule
import co.zsmb.kagu.services.storage.StorageModule
import co.zsmb.kagu.services.templates.TemplateModule
import co.zsmb.koinjs.dsl.module.Module
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
