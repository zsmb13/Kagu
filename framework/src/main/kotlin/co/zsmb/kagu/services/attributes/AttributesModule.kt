package co.zsmb.kagu.services.attributes

import co.zsmb.koinjs.dsl.module.applicationContext

val AttributesModule = applicationContext {
    bean { AttributesImpl() as Attributes }
}
