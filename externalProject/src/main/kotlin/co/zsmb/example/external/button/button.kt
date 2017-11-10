package co.zsmb.example.external.button

import co.zsmb.weblib.core.Component
import co.zsmb.weblib.core.Controller
import co.zsmb.weblib.core.di.inject
import co.zsmb.weblib.core.dom.onClick
import co.zsmb.weblib.core.lookup
import co.zsmb.weblib.services.logging.Logger
import org.w3c.dom.HTMLButtonElement

object ButtonComponent : Component(
        selector = "special-button",
        templateUrl = "components/button/button.html",
        controller = ::ButtonController
)

class ButtonController : Controller() {

    private val btn by lookup<HTMLButtonElement>("myButton")

    private val logger by inject<Logger>()

    private var clicks = 0

    override fun onCreate() {
        btn.onClick {
            logger.d(this, "Button clicked ${++clicks} times")
        }
    }

}
