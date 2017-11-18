package co.zsmb.kagu.services.http.backoff

import co.zsmb.kagu.core.util.Date

abstract class AbstractBackoffStrategy(val baseBackoffTime: Double) : BackoffStrategy {

    var errored = false
    var lastError = 0.0
    var currentBackoff = baseBackoffTime

    var recoveryCallInFlight = false

    private fun resetValues() {
        recoveryCallInFlight = false
        errored = false
        lastError = 0.0
        currentBackoff = baseBackoffTime
    }

    override fun onSuccess() {
        resetValues()
    }

    override fun onError() {
        recoveryCallInFlight = false

        if (errored) {
            currentBackoff = incrementBackoffTime(currentBackoff)
        } else {
            errored = true
        }

        lastError = Date().getTime()

    }

    abstract fun incrementBackoffTime(currentBackoff: Double): Double

    override fun allowsCalls(): Boolean {
        if (!errored) {
            return true
        }

        if (recoveryCallInFlight) {
            return false
        }

        val currentTime = Date().getTime()
        val delta = (currentTime - lastError)

        val allowed = delta >= currentBackoff
        if (allowed) {
            recoveryCallInFlight = true
        }
        return allowed
    }

}
