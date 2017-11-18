package co.zsmb.webmain.components.menu

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
