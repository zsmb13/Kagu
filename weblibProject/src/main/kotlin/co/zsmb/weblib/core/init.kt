package co.zsmb.weblib.core

import co.zsmb.weblib.jquery.jq
import co.zsmb.weblib.routing.Router
import co.zsmb.weblib.util.replaceWith
import co.zsmb.weblib.util.visitChildrenThat
import org.w3c.dom.Element
import org.w3c.dom.events.Event
import kotlin.browser.document
import kotlin.browser.window

private fun onLoad(actions: (Event) -> Unit) {
    window.onload = actions
}

fun init(comps: Set<Component>, after: () -> Unit) = onLoad {

    Router.cleanUrl()
    //Router.setHash("/button")

    val selectors = comps
            .map(Component::selector)
            .map(String::toUpperCase)
            .toSet()
    val compsMap = comps.associateBy(Component::selector)

    DomInjector.init(selectors, compsMap)
    DomInjector.injectComponents(document)

    initApp()

    after()

}

private fun initApp() {
    document.visitChildrenThat(
            predicate = { it.nodeName == "app-root".toUpperCase() },
            action = {

                val newDiv: Element
                if (Router.recognizesRoute()) {
                    newDiv = Router.getAppElement()
                } else {
                    newDiv = jq.parseHTML("<div></div>")[0] as Element
                    newDiv.innerHTML = Router.getHash()
                }

                DomInjector.injectComponents(newDiv)

                it.replaceWith(newDiv)
            }
    )
}
