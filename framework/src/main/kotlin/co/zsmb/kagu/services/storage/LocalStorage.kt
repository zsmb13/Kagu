package co.zsmb.kagu.services.storage

interface LocalStorage {

    operator fun get(key: String): String?
    operator fun set(key: String, value: String)

    fun remove(key: String)
    operator fun minusAssign(key: String)

}
