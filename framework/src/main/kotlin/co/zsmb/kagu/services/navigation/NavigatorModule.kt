package co.zsmb.kagu.services.navigation

import co.zsmb.koinjs.dsl.module.applicationContext

val NavigatorModule = applicationContext {
    bean { NavigatorImpl as Navigator }
}
