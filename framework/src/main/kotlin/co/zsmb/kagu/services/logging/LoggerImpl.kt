package co.zsmb.kagu.services.logging

internal object LoggerImpl : Logger {

    override fun v(message: String) = v(null, message)
    override fun v(source: Any?, message: String) = log(Logger.Level.VERBOSE, "V", source, message)

    override fun i(message: String) = i(null, message)
    override fun i(source: Any?, message: String) = log(Logger.Level.INFO, "I", source, message)

    override fun d(message: String) = d(null, message)
    override fun d(source: Any?, message: String) = log(Logger.Level.DEBUG, "D", source, message)

    override fun e(message: String) = e(null, message)
    override fun e(source: Any?, message: String) = log(Logger.Level.ERROR, "E", source, message)

    private fun log(messageLevel: Logger.Level, prefix: String, source: Any?, message: String) {
        if (messageLevel > level) {
            return
        }

        val formattedMessage =
                if (source == null) {
                    "[$prefix]: $message"
                } else {
                    "[$prefix/${source::class.simpleName}]: $message"
                }

        when (messageLevel) {
            Logger.Level.VERBOSE -> console.log(formattedMessage)
            Logger.Level.INFO -> console.info(formattedMessage)
            Logger.Level.DEBUG -> console.warn(formattedMessage)
            Logger.Level.ERROR -> console.error(formattedMessage)
        }
    }

    override var level: Logger.Level = Logger.Level.VERBOSE

}
