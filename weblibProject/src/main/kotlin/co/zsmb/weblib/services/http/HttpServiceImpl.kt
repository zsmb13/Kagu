package co.zsmb.weblib.services.http

import co.zsmb.weblib.core.jquery.JQueryAjaxSettings
import co.zsmb.weblib.core.jquery.JQueryXHR
import co.zsmb.weblib.core.jquery.jQuery
import co.zsmb.weblib.services.http.backoff.BackoffStrategy
import co.zsmb.weblib.services.http.backoff.ExponentialBackoffStrategy
import co.zsmb.weblib.services.http.backoff.LinearBackoffStrategy
import co.zsmb.weblib.services.http.backoff.NoBackoffStrategy

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

    private fun JQueryXHR.withCallbacks(onSuccess: (String) -> Unit, onError: (String) -> Unit) {
        this.done { result ->
            realBackoffStrategy.onSuccess()
            onSuccess(JSON.stringify(result))
        }
        this.fail { error ->
            realBackoffStrategy.onError()
            onError(JSON.stringify(error))
        }
    }

    override fun get(url: String,
                     headers: List<Header>,
                     onSuccess: (String) -> Unit,
                     onError: (String) -> Unit) {
        if (!realBackoffStrategy.allowsCalls()) {
            onError(BackoffStrategy.ERROR_MESSAGE)
            return
        }

        val s = createAjaxSettings(url, "GET", headers)
        jQuery.ajax(s).withCallbacks(onSuccess, onError)
    }

    override fun post(url: String,
                      body: Any,
                      headers: List<Header>,
                      onSuccess: (String) -> Unit,
                      onError: (String) -> Unit) {
        if (!realBackoffStrategy.allowsCalls()) {
            onError(BackoffStrategy.ERROR_MESSAGE)
            return
        }

        val s = createAjaxSettings(url, "POST", headers, body)
        jQuery.ajax(s).withCallbacks(onSuccess, onError)
    }

    override fun put(url: String,
                     body: Any,
                     headers: List<Header>,
                     onSuccess: (String) -> Unit,
                     onError: (String) -> Unit) {
        if (!realBackoffStrategy.allowsCalls()) {
            onError(BackoffStrategy.ERROR_MESSAGE)
            return
        }

        val s = createAjaxSettings(url, "PUT", headers, body)
        jQuery.ajax(s).withCallbacks(onSuccess, onError)
    }

    override fun delete(url: String,
                        headers: List<Header>,
                        onSuccess: (String) -> Unit,
                        onError: (String) -> Unit) {
        if (!realBackoffStrategy.allowsCalls()) {
            onError(BackoffStrategy.ERROR_MESSAGE)
            return
        }

        val s = createAjaxSettings(url, "DELETE", headers)
        jQuery.ajax(s).withCallbacks(onSuccess, onError)
    }

    private var realBackoffStrategy: BackoffStrategy = NoBackoffStrategy()

    override var backoffStrategy: HttpService.BackoffStrategy = HttpService.BackoffStrategy.None
        set(value) {
            realBackoffStrategy = when (value) {
                HttpService.BackoffStrategy.None -> NoBackoffStrategy()
                HttpService.BackoffStrategy.Linear -> LinearBackoffStrategy()
                HttpService.BackoffStrategy.Exponential -> ExponentialBackoffStrategy()
            }
        }

}
