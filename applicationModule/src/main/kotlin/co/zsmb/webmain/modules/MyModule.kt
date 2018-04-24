package co.zsmb.webmain.modules

import co.zsmb.koinjs.dsl.module.applicationContext
import co.zsmb.webmain.services.HttpTestService

val MyModule = applicationContext {
    bean { HttpTestService(get(), get()) }
}
