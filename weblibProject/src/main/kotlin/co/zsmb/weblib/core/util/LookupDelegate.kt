package co.zsmb.weblib.core.util

import co.zsmb.weblib.core.Controller
import co.zsmb.weblib.core.jquery.JQ
import kotlin.reflect.KProperty

fun <T> Controller.lookup(id: String): LookupDelegate<T> {
    return LookupDelegate(id, this)
}

fun <T> Controller.findById(id: String): T {
    val context = JQ.select(root)
    val query = "[data-kt-id='$id']"

    val results = JQ.select(query, context)

    val firstResult = results[0]

    @Suppress("UNCHECKED_CAST")
    return firstResult as T
}

class LookupDelegate<T>(id: String, ctrl: Controller) {

    @Suppress("UNCHECKED_CAST")
    val value by lazy {
        ctrl.findById<T>(id)
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value
    }

}
