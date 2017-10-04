package co.zsmb.weblib.services.logging

interface Logger {

    fun d(message: String)

    fun d(source: Any, message: String)

}
