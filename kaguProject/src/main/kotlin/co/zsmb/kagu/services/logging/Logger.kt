package co.zsmb.kagu.services.logging

interface Logger {

    fun d(message: String)

    fun d(source: Any, message: String)

    fun i(message: String)

    fun i(source: Any, message: String)

    fun v(message: String)

    fun v(source: Any, message: String)

}
