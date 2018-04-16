package co.zsmb.kagu.services.logging

import co.zsmb.koinjs.dsl.module.Module

internal object LoggerModule : Module() {

    override fun context() = declareContext {
        provide { LoggerImpl as Logger }
    }

}
