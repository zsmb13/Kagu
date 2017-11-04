package co.zsmb.weblib.core.di

import co.zsmb.weblib.internals.di.KaguKoin

inline fun <reified T> inject(): Lazy<T> = kotlin.lazy { KaguKoin.koin.get<T>() }
