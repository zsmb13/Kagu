package co.zsmb.example.external.button

import co.zsmb.weblib.core.Component
import co.zsmb.weblib.core.Controller
import co.zsmb.weblib.core.di.inject
import co.zsmb.weblib.core.lookup
import co.zsmb.weblib.services.logging.Logger
import org.w3c.dom.HTMLButtonElement
import kotlin.js.Date

object ButtonComponent : Component(
        selector = "special-button",
        templateUrl = "components/button/button.html",
        controller = ::ButtonController
)

class ButtonController : Controller() {

    val btn by lookup<HTMLButtonElement>("specialButton")

    val logger by inject<Logger>()

    var clicks = 0

    override fun onCreate() {
        logger.d(this, "ButtonController init")

        val time = Date().getTime()

        btn.id = "$time"

        btn.onclick = {
            logger.d(this, "clicked the special button with id $time")
            btn.innerHTML = "Clicked ${++clicks} times"
            Unit
        }
    }

    override fun onAdded() {
        logger.d(this, "onAdded")
    }

    override fun onRemoved() {
        logger.d(this, "onRemoved")
    }

}
