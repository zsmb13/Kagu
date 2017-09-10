package co.zsmb.example.external.button

import co.zsmb.weblib.core.Component
import co.zsmb.weblib.core.Controller
import co.zsmb.weblib.core.lookup
import org.w3c.dom.HTMLButtonElement
import kotlin.js.Date

class ButtonComponent : Component(
        selector = "special-button",
        templateUrl = "components/button/button.html",
        controller = ::ButtonController
)

class ButtonController : Controller() {

    val btn by lookup<HTMLButtonElement>("specialButton")

    override fun onInit() {
        println("ButtonController init")

        println(btn)

        val time = Date().getTime()
        println(time)

        btn.id = "$time"

        btn.onclick = {
            println("clicked the special button with id $time")
        }
    }

}
