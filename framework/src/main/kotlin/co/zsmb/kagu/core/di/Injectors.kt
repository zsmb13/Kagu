package co.zsmb.kagu.core.di

import co.zsmb.kagu.core.Controller
import co.zsmb.kagu.internals.di.KaguKoin
import co.zsmb.kagu.services.ControllerService

inline fun <reified T> inject(): Lazy<T> = kotlin.lazy { KaguKoin.koin.get<T>() }

inline fun <reified T> Controller.inject(): Lazy<T> = kotlin.lazy {
    val result = KaguKoin.koin.get<T>()
    if (result is ControllerService) {
        result.controller = this
    }
    result
}
