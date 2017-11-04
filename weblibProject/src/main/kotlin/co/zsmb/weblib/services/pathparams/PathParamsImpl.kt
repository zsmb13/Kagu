package co.zsmb.weblib.services.pathparams

import co.zsmb.weblib.internals.routing.Router

internal object PathParamsImpl : PathParams {

    override fun getInt(paramName: String) = getString(paramName)?.toIntOrNull()

    override fun getString(paramName: String) = Router.currentStateParams[paramName]

    override fun getIntUnsafe(paramName: String) = getStringUnsafe(paramName).toInt()

    override fun getStringUnsafe(paramName: String) = Router.currentStateParams[paramName]!!

}
