package co.zsmb.weblib.di.logging

internal class LoggerImpl : Logger {

    override fun d(message: String) = console.log("[Debug] $message")

}
