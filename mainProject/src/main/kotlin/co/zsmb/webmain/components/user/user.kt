package co.zsmb.webmain.components.user

import co.zsmb.kagu.core.Component
import co.zsmb.kagu.core.Controller
import co.zsmb.kagu.core.di.inject
import co.zsmb.kagu.core.lookup
import co.zsmb.kagu.services.pathparams.PathParams
import org.w3c.dom.HTMLDivElement

object UserComponent : Component(
        selector = "user-component",
        templateUrl = "components/user/user.html",
        controller = ::UserController
)

class UserController : Controller() {

    val pathParams by inject<PathParams>()
    val userId = pathParams.getInt("userId")

    val userIdDisplay by lookup<HTMLDivElement>("user-id-div")

    override fun onCreate() {
        println("UserController init")
        println("userId is $userId")
        userIdDisplay.innerText = "user id is $userId"
    }

}
