package co.zsmb.weblib.services.http

import co.zsmb.weblib.core.jquery.JQueryAjaxSettings
import co.zsmb.weblib.core.jquery.jQuery

internal object HttpServiceImpl : HttpService {

    private fun getObject() = js("return {}")

    @Suppress("UNCHECKED_CAST_TO_NATIVE_INTERFACE")
    private fun ajaxSettings(setup: JQueryAjaxSettings.() -> Unit) =
            (getObject() as JQueryAjaxSettings).apply(setup)

    override fun get(url: String,
                     headers: List<Header>,
                     callback: (String) -> Unit) {
        val s = ajaxSettings {
            this.url = url
            this.method = "GET"
            this.beforeSend = { xhr, _ ->
                headers.forEach { (name, value) ->
                    xhr.setRequestHeader(name, value)
                }
            }
        }

        jQuery.ajax(s).done(callback)
    }

    override fun post(url: String, body: Any,
                      headers: List<Header>,
                      callback: (String) -> Unit) {
        val s = ajaxSettings {
            this.url = url
            this.method = "GET"
            this.data = JSON.stringify(body)
            this.beforeSend = { xhr, _ ->
                headers.forEach { (name, value) ->
                    xhr.setRequestHeader(name, value)
                }
            }
        }

        jQuery.ajax(s).done(callback)
    }

}
