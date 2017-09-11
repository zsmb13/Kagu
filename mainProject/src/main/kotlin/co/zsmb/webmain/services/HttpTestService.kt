package co.zsmb.webmain.services

import co.zsmb.weblib.di.logging.Logger
import co.zsmb.weblib.jquery.JQueryAjaxSettings
import co.zsmb.weblib.jquery.jQuery

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
