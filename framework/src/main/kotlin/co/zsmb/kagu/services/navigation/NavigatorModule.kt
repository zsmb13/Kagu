package co.zsmb.kagu.services.navigation

import co.zsmb.koinjs.dsl.module.Module

internal object NavigatorModule : Module() {

    override fun context() = declareContext {
        provide { NavigatorImpl as Navigator }
    }

}
