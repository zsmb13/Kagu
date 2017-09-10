package co.zsmb.webmain.components.user

import co.zsmb.weblib.core.Component
import co.zsmb.weblib.core.Controller

class UserComponent : Component(
        selector = "user-component",
        templateUrl = "components/user/user.html",
        controller = ::UserController
)

class UserController : Controller() {

    override fun onInit() {
        println("UserController init")
    }

}
