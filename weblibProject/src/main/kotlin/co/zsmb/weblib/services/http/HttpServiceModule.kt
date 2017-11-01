package co.zsmb.weblib.services.http

import co.zsmb.koinjs.dsl.module.Module
import co.zsmb.weblib.services.logging.LoggerModule

internal object HttpServiceModule : Module() {

    override fun context() =
            LoggerModule.declareContext {
                provide { createHttpService() }
            }

    private fun createHttpService(): HttpService = HttpServiceImpl

}
