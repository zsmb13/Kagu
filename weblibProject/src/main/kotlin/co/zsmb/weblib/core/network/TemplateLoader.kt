package co.zsmb.weblib.core.network

import co.zsmb.weblib.core.jquery.JQueryAjaxSettings
import co.zsmb.weblib.core.jquery.jQuery
import co.zsmb.weblib.core.jquery.jq.parseHTML
import org.w3c.dom.Element

internal object TemplateLoader {

    private fun createEmptyObject() = js("return {}")

    private val cache = mutableMapOf<String, String>()

    fun get(url: String, callback: (Element) -> Unit) {

        if (cache.containsKey(url)) {
            returnResult(cache[url]!!, callback)
            return
        }

        val settings: JQueryAjaxSettings = createEmptyObject()
        val s = settings.apply {
            this.url = url
            this.method = "GET"
        }

        jQuery.ajax(s).done { html ->
            cache[url] = html
            returnResult(html, callback)
        }

    }

    private fun returnResult(html: String, callback: (Element) -> Unit) {
        val elems = parseHTML(html)
        // TODO wrap in a div if length is not 1 ?
        val root = elems[0] as Element
        callback(root)
    }

}
