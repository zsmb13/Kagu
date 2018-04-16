package co.zsmb.kagu.services.http

import co.zsmb.koinjs.dsl.module.Module

internal object HttpServiceModule : Module() {

    override fun context() = declareContext {
        provide { HttpServiceImpl as HttpService }
    }

}
