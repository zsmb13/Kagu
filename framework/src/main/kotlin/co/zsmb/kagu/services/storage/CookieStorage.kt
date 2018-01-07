package co.zsmb.kagu.services.storage

import co.zsmb.kagu.core.util.Date

interface CookieStorage {

    operator fun get(key: String): String?
    operator fun set(key: String, value: String)

    fun set(key: String, value: String, expires: Date? = null, path: String? = null)

    fun remove(key: String)
    operator fun minusAssign(key: String)

}
