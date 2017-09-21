package co.zsmb.webmain.modules

import co.zsmb.koinjs.dsl.module.Module
import co.zsmb.webmain.services.HttpTestService

object MyModule : Module() {

    override fun context() = declareContext {
        provide { HttpTestService(get()) }
    }

}
