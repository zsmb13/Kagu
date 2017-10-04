package co.zsmb.webmain.services

import co.zsmb.weblib.services.http.HttpService
import co.zsmb.weblib.services.logging.Logger

class HttpTestService(val logger: Logger, val httpService: HttpService) {

    companion object {
        const val url = "https://cors-test.appspot.com/test"
    }

    fun getPublicObject() {

        val headers = listOf(
                "kittens" to "three",
                "woofers" to "five"
        )

        httpService.get(url, headers) { response ->
            logger.d(this, JSON.stringify(response))
        }

    }

}
