package co.zsmb.kagu.services.attributes

import co.zsmb.kagu.core.Controller

internal class AttributesImpl : Attributes {

    override var controller: Controller? = null

    override fun getInt(attrName: String) = getString(attrName)?.toIntOrNull()

    override fun getString(attrName: String) = controller?.root?.getAttribute("data-kt-$attrName")

    override fun getIntUnsafe(attrName: String) = getStringUnsafe(attrName).toInt()

    override fun getStringUnsafe(attrName: String) = getString(attrName)!!

}
