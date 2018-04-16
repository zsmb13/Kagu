package co.zsmb.kagu.services.templates

import co.zsmb.koinjs.dsl.module.Module

internal object TemplateModule : Module() {

    override fun context() = declareContext {
        provide { TemplateLoaderImpl as TemplateLoader }
    }

}
