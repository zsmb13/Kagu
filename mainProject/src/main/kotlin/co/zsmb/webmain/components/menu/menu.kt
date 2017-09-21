package co.zsmb.webmain.components.menu

import co.zsmb.weblib.core.Component
import co.zsmb.weblib.core.controller.Controller

object MenuComponent : Component(
        selector = "menu-component",
        templateUrl = "components/menu/menu.html",
        controller = ::MenuController
)

class MenuController : Controller() {

    override fun onInit() {
        println("MenuController init")

    }

}
