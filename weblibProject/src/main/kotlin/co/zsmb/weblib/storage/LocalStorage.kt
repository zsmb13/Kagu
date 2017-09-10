package co.zsmb.weblib.storage

import kotlin.browser.localStorage

object LocalStorage {

    fun get() = localStorage.getItem("some_key")

    fun put() {
        localStorage.setItem("some_key", "some valuueee")
    }

}
