package co.zsmb.weblib.services.http.backoff

class ExponentialBackoffStrategy : AbstractBackoffStrategy(BASE_MS) {

    companion object {
        const val BASE_MS = 25.0
        const val MULTIPLIER = 1.5
    }

    override fun incrementBackoffTime(currentBackoff: Double) = currentBackoff * MULTIPLIER

}
