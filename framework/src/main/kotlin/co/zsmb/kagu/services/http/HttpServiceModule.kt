package co.zsmb.kagu.services.http

import co.zsmb.koinjs.dsl.module.applicationContext

val HttpServiceModule = applicationContext {
    bean { HttpServiceImpl as HttpService }
}
