package co.zsmb.kagu.services.storage

import co.zsmb.koinjs.dsl.module.applicationContext

val StorageModule = applicationContext {
    bean { LocalStorageImpl as LocalStorage }
    bean { CookieStorageImpl as CookieStorage }
}
