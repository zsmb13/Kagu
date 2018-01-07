package co.zsmb.webmain.components.menu

import co.zsmb.kagu.core.Component
import co.zsmb.kagu.core.Controller

object MenuComponent : Component(
        selector = "menu-component",
        templateUrl = "components/menu/menu.html",
        controller = ::MenuController
)

class MenuController : Controller() {

    override fun onCreate() {
        println("MenuController init")
    }

}
