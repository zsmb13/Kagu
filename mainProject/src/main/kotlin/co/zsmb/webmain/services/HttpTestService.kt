package co.zsmb.webmain.services

import co.zsmb.weblib.core.jquery.JQueryAjaxSettings
import co.zsmb.weblib.core.jquery.jQuery
import co.zsmb.weblib.services.logging.Logger

class HttpTestService(val logger: Logger) {

    fun getPublicObject() {

        fun getObject() = js("return {}")

        val settings: JQueryAjaxSettings = getObject()

        val s = settings.apply {
            this.url = "https://cors-test.appspot.com/test"
            this.method = "GET"
        }

        jQuery.ajax(s).done {
            logger.d(JSON.stringify(it))
        }
    }

}
