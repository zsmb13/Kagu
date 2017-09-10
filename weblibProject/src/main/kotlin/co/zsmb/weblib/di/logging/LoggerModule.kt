package co.zsmb.weblib.di.logging

import co.zsmb.koinjs.dsl.module.Module

class LoggerModule : Module() {

    override fun context() =
            declareContext {
                provide { createLogger() }
            }

    private fun createLogger(): Logger = LoggerImpl()

}
