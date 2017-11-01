package co.zsmb.weblib.services.templates

import co.zsmb.weblib.core.InternalLogger
import co.zsmb.weblib.core.jquery.JQ.parseHTML
import co.zsmb.weblib.core.jquery.JQueryAjaxSettings
import co.zsmb.weblib.core.jquery.jQuery
import org.w3c.dom.Element

private typealias Callback = (Element) -> Unit

internal object TemplateLoaderImpl : TemplateLoader {

    private fun createEmptyObject() = js("return {}")

    private val cache = mutableMapOf<String, String>()
    private val onGoingCalls = mutableMapOf<String, MutableList<Callback>>()

    override fun get(url: String, callback: (Element) -> Unit) {

        if (cache.containsKey(url)) {
            InternalLogger.d(this, "Cache had key $url")
            returnResult(cache[url]!!, callback)
            return
        }

        if (onGoingCalls.containsKey(url)) {
            InternalLogger.d(this, "There were in flight calls for $url")
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
            InternalLogger.d(this, "Added to cache key $url")

            onGoingCalls[url]!!.forEach { callback ->
                returnResult(html, callback)
            }
            onGoingCalls.remove(url)
        }

    }

    private fun returnResult(html: String, callback: (Element) -> Unit) {
        val elems = parseHTML(html)
        // TODO wrap in a div if length is not 1 ?
        val root = elems[0] as Element
        callback(root)
    }

}