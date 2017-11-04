package co.zsmb.weblib.services.http.backoff

class LinearBackoffStrategy : AbstractBackoffStrategy(BASE_MS) {

    companion object {
        const val BASE_MS = 200.0
    }

    override fun incrementBackoffTime(currentBackoff: Double) = currentBackoff + BASE_MS

}
