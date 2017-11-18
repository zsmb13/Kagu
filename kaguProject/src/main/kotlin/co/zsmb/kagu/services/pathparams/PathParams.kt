package co.zsmb.kagu.services.pathparams

interface PathParams {

    fun getInt(paramName: String): Int?

    fun getIntUnsafe(paramName: String): Int

    fun getString(paramName: String): String?

    fun getStringUnsafe(paramName: String): String

}
