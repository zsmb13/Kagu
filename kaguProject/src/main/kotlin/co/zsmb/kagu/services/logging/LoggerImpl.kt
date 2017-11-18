package co.zsmb.kagu.services.logging

import co.zsmb.kagu.core.Controller
import co.zsmb.kagu.core.di.inject

internal object LoggerImpl : Logger {

    override fun d(message: String) = console.log("[D] $message")

    override fun d(source: Any, message: String) {
        val sourceName = source::class.simpleName
        console.log("[D/$sourceName]: $message")
    }

}

class LoggerTestController : Controller() {
    private val logger by inject<Logger>()

    override fun onCreate() {
        super.onCreate()
        logger.d(this, "Created")
    }
}
