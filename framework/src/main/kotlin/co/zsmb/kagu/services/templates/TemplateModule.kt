package co.zsmb.kagu.services.templates

import co.zsmb.koinjs.dsl.module.applicationContext

val TemplateModule = applicationContext {
    bean { TemplateLoaderImpl as TemplateLoader }
}
