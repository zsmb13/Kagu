package co.zsmb.weblib.core

import co.zsmb.weblib.routing.Router
import org.w3c.dom.events.Event
import kotlin.browser.document
import kotlin.browser.window

private fun onLoad(actions: (Event) -> Unit) {
    window.onload = actions
}

fun initAsync(comps: Set<Component>, after: () -> Unit) = onLoad {

    Router.cleanUrl()

    val selectors = comps
            .map(Component::selector)
            .map(String::toUpperCase)
            .toSet()
    val compsMap = comps.associateBy(Component::selector)

    DomInjector.init(selectors, compsMap)
    DomInjector.injectComponentsAsync(document)

    Router.refresh()

    after()

}
