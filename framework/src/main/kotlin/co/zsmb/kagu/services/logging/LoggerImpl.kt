package co.zsmb.kagu.services.logging

internal object LoggerImpl : Logger {

    override fun i(message: String) = log("I", message)

    override fun i(source: Any, message: String) = log("I", source, message)

    override fun v(message: String) = log("V", message)

    override fun v(source: Any, message: String) = log("V", source, message)

    override fun d(message: String) = log("D", message)

    override fun d(source: Any, message: String) = log("D", source, message)

    private fun log(prefix: String, message: String) {
        console.log("[$prefix]: $message")
    }

    private fun log(prefix: String, source: Any, message: String) {
        val sourceName = source::class.simpleName
        console.log("[$prefix/$sourceName]: $message")
    }

}
