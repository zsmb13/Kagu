package co.zsmb.weblib.services.logging

internal object LoggerImpl : Logger {

    override fun d(message: String) = console.log("[Debug] $message")

    override fun d(source: Any, message: String) {
        val sourceName = source::class.simpleName
        console.log("[D/$sourceName]: $message")
    }

}
