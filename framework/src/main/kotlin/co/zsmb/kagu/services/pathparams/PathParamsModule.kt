package co.zsmb.kagu.services.pathparams

import co.zsmb.koinjs.dsl.module.applicationContext

val PathParamsModule = applicationContext {
    factory { PathParamsImpl as PathParams }
}
