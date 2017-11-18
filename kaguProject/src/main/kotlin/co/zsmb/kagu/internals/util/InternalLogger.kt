package co.zsmb.kagu.internals.util

internal object InternalLogger {

    fun d(source: Any, message: String) {
        val sourceName = source::class.simpleName
        console.log("[Internal/$sourceName]: $message")
    }

}
