package co.zsmb.weblib.di.logging

interface Logger {

    fun d(message: String)

    fun d(source: Any, message: String)

}
