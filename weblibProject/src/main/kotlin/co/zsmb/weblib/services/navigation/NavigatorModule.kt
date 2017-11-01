package co.zsmb.weblib.services.navigation

import co.zsmb.koinjs.dsl.module.Module

internal object NavigatorModule : Module() {

    override fun context() = declareContext {
        provide { createNavigator() }
    }

    private fun createNavigator(): Navigator = NavigatorImpl

}
