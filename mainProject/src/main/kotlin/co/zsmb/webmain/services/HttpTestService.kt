package co.zsmb.webmain.services

class HttpTestService(val logger: Logger, val httpService: HttpService) {

    fun performTest() {
        httpService.get("https://cors-test.appspot.com/test",
                onSuccess = { response ->
                    logger.d(this, JSON.stringify(response))
                },
                onError = { error ->
                    logger.d(this, "Http error: $error")
                })
    }

}
