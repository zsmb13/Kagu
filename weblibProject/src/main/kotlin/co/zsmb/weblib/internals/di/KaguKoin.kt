package co.zsmb.weblib.internals.di

import co.zsmb.koinjs.Koin
import co.zsmb.koinjs.KoinContext
import co.zsmb.koinjs.dsl.module.Module

@PublishedApi
internal object KaguKoin {

    private val modules = mutableListOf<Module>()

    internal fun init(modules: MutableSet<Module>) {
        KaguKoin.modules += modules
    }

    @PublishedApi
    internal val koin: KoinContext by lazy { Koin().build(modules) }

}
