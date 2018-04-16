package co.zsmb.kagu.services.attributes

import co.zsmb.koinjs.dsl.module.Module

object AttributesModule : Module() {

    override fun context() = declareContext {
        provide { AttributesImpl() as Attributes }
    }

}
