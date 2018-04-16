package co.zsmb.webmain.components.menu

import co.zsmb.kagu.core.Component
import co.zsmb.kagu.core.Controller
import co.zsmb.kagu.core.di.inject
import co.zsmb.kagu.services.attributes.Attributes
import co.zsmb.kagu.services.logging.Logger

object MenuComponent : Component(
        selector = "menu-component",
        templateUrl = "components/menu/menu.html",
        controller = ::MenuController
)

class MenuController : Controller() {

    private val tagParams by inject<Attributes>()
    private val logger by inject<Logger>()

    override fun onCreate() {
        logger.d(this, "tag param: ${tagParams.getString("foo")}")
    }

}
