package co.zsmb.weblib.core.controller

import co.zsmb.weblib.jquery.jq
import kotlin.reflect.KProperty

fun <T> Controller.lookup(id: String): LookupDelegate<T> {
    return LookupDelegate(id, this)
}

class LookupDelegate<T>(id: String, ctrl: Controller) {

    @Suppress("UNCHECKED_CAST")
    val value by lazy {
        val context = jq.select(ctrl.root)
        val query = "[data-kt-id='$id']"

        val result = jq.select(query, context)

        result[0]!! as T
    }

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return value
    }

}
