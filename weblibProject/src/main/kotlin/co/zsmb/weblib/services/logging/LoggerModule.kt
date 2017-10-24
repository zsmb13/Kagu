package co.zsmb.weblib.services.logging

import co.zsmb.koinjs.dsl.module.Module

internal object LoggerModule : Module() {

    override fun context() =
            declareContext {
                provide { createLogger() }
            }

    private fun createLogger(): Logger = LoggerImpl

}
