package co.zsmb.kagu.internals.di

import co.zsmb.koinjs.KoinContext
import co.zsmb.koinjs.dsl.module.Module
import co.zsmb.koinjs.standalone.StandAloneContext.startKoin

@PublishedApi
internal object KaguKoin {

    private val modules = mutableListOf<Module>()

    internal fun init(modules: MutableSet<Module>) {
        KaguKoin.modules += modules
    }

    @PublishedApi
    internal val koin: KoinContext by lazy { startKoin(modules).koinContext }

}
