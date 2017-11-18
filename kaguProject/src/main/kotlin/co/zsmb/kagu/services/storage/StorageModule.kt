package co.zsmb.kagu.services.storage

import co.zsmb.kagu.services.messaging.MessageModule
import co.zsmb.koinjs.dsl.module.Module

internal object StorageModule : Module() {

    override fun context() = MessageModule.declareContext {
        provide { createLocalStorage() }
        provide { createCookieStorage() }
    }

    fun createLocalStorage(): LocalStorage = LocalStorageImpl

    fun createCookieStorage(): CookieStorage = CookieStorageImpl

}
