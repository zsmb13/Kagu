package co.zsmb.kagu.services.logging

interface Logger {

    fun v(message: String)
    fun v(source: Any?, message: String)

    fun i(message: String)
    fun i(source: Any?, message: String)

    fun d(message: String)
    fun d(source: Any?, message: String)

    fun e(message: String)
    fun e(source: Any?, message: String)

    enum class Level {
        ERROR,
        DEBUG,
        INFO,
        VERBOSE
    }

    var level: Level

}
