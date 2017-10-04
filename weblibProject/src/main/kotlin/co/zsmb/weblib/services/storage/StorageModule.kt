package co.zsmb.weblib.services.storage

import co.zsmb.koinjs.dsl.module.Module
import co.zsmb.weblib.services.messaging.MessageModule

object StorageModule : Module() {

    override fun context() = MessageModule.declareContext {
        provide { createLocalStorage() }
        provide { createCookieStorage() }
    }

    fun createLocalStorage(): LocalStorage = LocalStorageImpl

    fun createCookieStorage(): CookieStorage = CookieStorageImpl

}
