package co.zsmb.weblib.services.templates

import co.zsmb.weblib.core.jquery.JQ.parseHTML
import co.zsmb.weblib.core.jquery.JQueryAjaxSettings
import co.zsmb.weblib.core.jquery.jQuery
import org.w3c.dom.HTMLElement

private typealias Callback = (HTMLElement) -> Unit

internal object TemplateLoaderImpl : TemplateLoader {

    private fun createEmptyObject() = js("return {}")

    private val cache = mutableMapOf<String, String>()
    private val onGoingCalls = mutableMapOf<String, MutableList<Callback>>()

    override fun get(url: String, callback: (HTMLElement) -> Unit) {

        if (cache.containsKey(url)) {
            returnResult(cache[url]!!, callback)
            return
        }

        if (onGoingCalls.containsKey(url)) {
            onGoingCalls[url]!! += callback
            return
        }

        onGoingCalls[url] = mutableListOf(callback)

        val settings: JQueryAjaxSettings = createEmptyObject()
        val s = settings.apply {
            this.url = url
            this.method = "GET"
        }

        jQuery.ajax(s).done { html ->
            cache[url] = html

            onGoingCalls[url]!!.forEach { callback ->
                returnResult(html, callback)
            }
            onGoingCalls.remove(url)
        }

    }

    private fun returnResult(html: String, callback: (HTMLElement) -> Unit) {
        val elems = parseHTML(html.trim())
        // TODO wrap in a div if length is not 1 ?
        val root = elems[0] as HTMLElement
        callback(root)
    }

}
