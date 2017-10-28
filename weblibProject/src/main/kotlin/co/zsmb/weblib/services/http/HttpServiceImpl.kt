package co.zsmb.weblib.services.http

import co.zsmb.weblib.core.jquery.JQueryAjaxSettings
import co.zsmb.weblib.core.jquery.JQueryXHR
import co.zsmb.weblib.core.jquery.jQuery

internal object HttpServiceImpl : HttpService {

    private fun getObject() = js("return {}")

    @Suppress("UNCHECKED_CAST_TO_NATIVE_INTERFACE")
    private fun ajaxSettings(setup: JQueryAjaxSettings.() -> Unit) =
            (getObject() as JQueryAjaxSettings).apply(setup)

    private fun createAjaxSettings(url: String, method: String, headers: List<Header>, body: Any? = null) =
            ajaxSettings {
                this.url = url
                this.method = method
                this.beforeSend = { xhr, _ ->
                    headers.forEach { (name, value) ->
                        xhr.setRequestHeader(name, value)
                    }
                }
                if (body != null) {
                    this.data = JSON.stringify(body)
                }
            }

    private fun JQueryXHR.withCallback(callback: (String) -> Unit) {
        this.done { result ->
            callback(JSON.stringify(result))
        }
    }

    override fun get(url: String,
                     headers: List<Header>,
                     callback: (String) -> Unit) {
        val s = createAjaxSettings(url, "GET", headers)
        jQuery.ajax(s).withCallback(callback)
    }

    override fun post(url: String,
                      body: Any,
                      headers: List<Header>,
                      callback: (String) -> Unit) {
        val s = createAjaxSettings(url, "POST", headers, body)
        jQuery.ajax(s).withCallback(callback)
    }

    override fun put(url: String,
                     body: Any,
                     headers: List<Header>,
                     callback: (String) -> Unit) {
        val s = createAjaxSettings(url, "PUT", headers, body)
        jQuery.ajax(s).withCallback(callback)
    }

    override fun delete(url: String,
                        headers: List<Header>,
                        callback: (String) -> Unit) {
        val s = createAjaxSettings(url, "DELETE", headers)
        jQuery.ajax(s).withCallback(callback)
    }

}
