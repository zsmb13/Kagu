package co.zsmb.weblib.services.storage

import kotlin.browser.localStorage

internal object LocalStorageImpl : LocalStorage {

    override operator fun get(key: String) = localStorage.getItem(key)

    override operator fun set(key: String, value: String) = localStorage.setItem(key, value)

    override fun remove(key: String) = localStorage.removeItem(key)

    override operator fun minusAssign(key: String) = remove(key)

}
