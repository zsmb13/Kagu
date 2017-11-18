package co.zsmb.example.external.button

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
