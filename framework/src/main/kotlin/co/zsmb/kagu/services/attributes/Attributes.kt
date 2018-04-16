package co.zsmb.kagu.services.attributes

import co.zsmb.kagu.services.ControllerService

interface Attributes : ControllerService {

    fun getInt(attrName: String): Int?

    fun getIntUnsafe(attrName: String): Int

    fun getString(attrName: String): String?

    fun getStringUnsafe(attrName: String): String

}
