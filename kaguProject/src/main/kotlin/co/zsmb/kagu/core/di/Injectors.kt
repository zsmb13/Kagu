package co.zsmb.kagu.core.di

import co.zsmb.kagu.internals.di.KaguKoin

inline fun <reified T> inject(): Lazy<T> = kotlin.lazy { KaguKoin.koin.get<T>() }
