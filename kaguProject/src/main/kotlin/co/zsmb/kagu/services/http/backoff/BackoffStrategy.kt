package co.zsmb.kagu.services.http.backoff

internal interface BackoffStrategy {

    companion object {
        val ERROR_MESSAGE = "Backoff prevented network call"
    }

    fun onSuccess()

    fun onError()

    fun allowsCalls(): Boolean

}
