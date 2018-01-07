package co.zsmb.kagu.services.storage

import co.zsmb.kagu.core.util.Date
import kotlin.browser.document

internal object CookieStorageImpl : CookieStorage {

    private val cookies by lazy {
        document.cookie
                .splitToSequence(";")
                .filter { it.isNotBlank() }
                .map { it.trim() }
                .map {
                    val (key, value) = it.split('=')
                    key to value
                }
                .toMap()
                .toMutableMap()
    }

    override fun get(key: String) = cookies[key]

    override fun set(key: String, value: String) {
        document.cookie = "$key=$value"
        cookies[key] = value
    }

    override fun set(key: String, value: String, expires: Date?, path: String?) {
        val keyValueStr = "$key=$value"
        val exprStr = expires?.let { "expires=${it.toUTCString()}" }
        val pathStr = path?.let { "path=$it" }

        val cookieStr = listOf(keyValueStr, exprStr, pathStr)
                .filterNotNull()
                .joinToString(separator = ";")

        document.cookie = cookieStr
        cookies[key] = value
    }

    override fun remove(key: String) {
        document.cookie = "$key=;expires=Thu, 01 Jan 1970 00:00:00 UTC"
        cookies.remove(key)
    }

    override fun minusAssign(key: String) = remove(key)

}
