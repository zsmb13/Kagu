package co.zsmb.weblib.di.logging

internal class LoggerImpl : Logger {

    override fun d(message: String) = console.log("[Debug] $message")

    override fun d(source: Any, message: String) {
        val sourceName = source::class.simpleName
        console.log("[D/$sourceName]: $message")
    }

}
