package co.zsmb.kagu.services.http

import co.zsmb.kagu.services.logging.LoggerModule
import co.zsmb.koinjs.dsl.module.Module

internal object HttpServiceModule : Module() {

    override fun context() =
            LoggerModule.declareContext {
                provide { createHttpService() }
            }

    private fun createHttpService(): HttpService = HttpServiceImpl

}
