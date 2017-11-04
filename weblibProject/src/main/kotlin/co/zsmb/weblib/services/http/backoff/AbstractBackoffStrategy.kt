package co.zsmb.weblib.services.http.backoff

import co.zsmb.weblib.core.util.Date

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
        //InternalLogger.d(this, "Successful call, reset backoff time")
        resetValues()
    }

    override fun onError() {
        recoveryCallInFlight = false

        if (errored) {
            val oldTime = currentBackoff
            val newTime = incrementBackoffTime(currentBackoff)
            //InternalLogger.d(this, "Repeated errored call, incrementing time from $oldTime ms to $newTime ms")

            currentBackoff = incrementBackoffTime(currentBackoff)
        } else {
            //InternalLogger.d(this, "First errored call, setting error flag, time is $currentBackoff")
            errored = true
        }

        lastError = Date().getTime()

        //InternalLogger.d(this, "Last error time is now $lastError")
    }

    abstract fun incrementBackoffTime(currentBackoff: Double): Double

    override fun allowsCalls(): Boolean {
        if (!errored) {
            //InternalLogger.d(this, "Call allowed, no errors in history")
            return true
        }

        if (recoveryCallInFlight) {
            //InternalLogger.d(this, "Call not allowed, recovery call is in flight")
            return false
        }

        val currentTime = Date().getTime()
        val delta = (currentTime - lastError)

        if (delta >= currentBackoff) {
            recoveryCallInFlight = true
            //InternalLogger.d(this, "Call allowed, $delta already passed since last error and backoff is $currentBackoff")
        } else {
            //InternalLogger.d(this, "Call not allowed, only $delta passed since last error and backoff is $currentBackoff")
        }
        return delta >= currentBackoff
    }

}
