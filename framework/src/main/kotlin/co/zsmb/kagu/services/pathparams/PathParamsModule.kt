package co.zsmb.kagu.services.pathparams

import co.zsmb.koinjs.dsl.module.Module

internal object PathParamsModule : Module() {

    override fun context() = declareContext {
        provide { PathParamsImpl as PathParams }
    }

}
