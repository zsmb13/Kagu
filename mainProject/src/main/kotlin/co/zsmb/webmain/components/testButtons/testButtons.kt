package co.zsmb.webmain.components.testButtons

import co.zsmb.weblib.core.Component
import co.zsmb.weblib.core.controller.Controller
import co.zsmb.weblib.core.controller.lookup
import co.zsmb.weblib.di.inject
import co.zsmb.weblib.di.logging.Logger
import co.zsmb.webmain.services.HttpTestService
import org.w3c.dom.HTMLButtonElement
import kotlin.browser.window

class TestButtonsComponent : Component(
        selector = "test-buttons",
        templateUrl = "components/testButtons/testButtons.html",
        controller = ::TestButtonsController
)

class TestButtonsController : Controller() {

    val btn by lookup<HTMLButtonElement>("menuBtn")
    val fetch by lookup<HTMLButtonElement>("fetchBtn")

    val logger by inject<Logger>()

    val poService by inject<HttpTestService>()

    override fun onInit() {
        println("TestButtonsController init")

        fetch.onclick = {
            println(poService.hashCode())
            poService.getPublicObject()
        }

        btn.onclick = {
            window.alert("clicked!")
            logger.d("btn clicked")
        }
    }

}
