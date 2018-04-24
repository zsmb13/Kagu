package co.zsmb.kagu.services.logging

import co.zsmb.koinjs.dsl.module.applicationContext

val LoggerModule = applicationContext {
    bean { LoggerImpl as Logger }
}
