package co.zsmb.weblib.network

import co.zsmb.weblib.jquery.JQueryAjaxSettings
import co.zsmb.weblib.jquery.jQuery
import co.zsmb.weblib.jquery.jq.parseHTML
import org.w3c.dom.Element

private fun createEmptyObject() = js("return {}")

fun fetchHtml(url: String, callback: (Element) -> Unit) {

    val settings: JQueryAjaxSettings = createEmptyObject()

    val s = settings.apply {
        this.url = url
        this.method = "GET"
    }

    jQuery.ajax(s).done {
        val elems = parseHTML(it)
        // TODO wrap in a div if length is not 1 ?
        val root = elems[0] as Element
        callback(root)
    }

}
