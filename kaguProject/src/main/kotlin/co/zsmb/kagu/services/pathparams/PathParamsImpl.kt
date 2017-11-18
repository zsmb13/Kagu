package co.zsmb.kagu.services.pathparams

import co.zsmb.kagu.internals.routing.Router

internal object PathParamsImpl : PathParams {

    override fun getInt(paramName: String) = getString(paramName)?.toIntOrNull()

    override fun getString(paramName: String) = Router.currentStateParams[paramName]

    override fun getIntUnsafe(paramName: String) = getStringUnsafe(paramName).toInt()

    override fun getStringUnsafe(paramName: String) = Router.currentStateParams[paramName]!!

}
