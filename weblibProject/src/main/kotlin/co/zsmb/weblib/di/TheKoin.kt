package co.zsmb.weblib.di

import co.zsmb.koinjs.Koin
import co.zsmb.koinjs.KoinContext
import co.zsmb.koinjs.dsl.module.Module
import co.zsmb.weblib.di.TheKoin.koin

@PublishedApi
internal object TheKoin {

    private val modules = mutableListOf<Module>()

    internal fun init(modules: MutableSet<Module>) {
        this.modules += modules
    }

    @PublishedApi
    internal val koin: KoinContext by lazy { Koin().build(modules) }

}

inline fun <reified T> inject(): Lazy<T> = kotlin.lazy { koin.get<T>() }
