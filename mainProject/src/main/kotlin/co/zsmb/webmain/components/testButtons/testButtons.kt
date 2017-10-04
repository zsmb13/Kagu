package co.zsmb.webmain.components.testButtons

import co.zsmb.weblib.core.Component
import co.zsmb.weblib.core.Controller
import co.zsmb.weblib.core.di.inject
import co.zsmb.weblib.core.util.findById
import co.zsmb.weblib.core.util.lookup
import co.zsmb.weblib.core.util.onClick
import co.zsmb.weblib.services.logging.Logger
import co.zsmb.weblib.services.messaging.MessageBroker
import co.zsmb.webmain.services.HttpTestService
import org.w3c.dom.HTMLButtonElement
import kotlin.browser.window
import kotlin.js.Date

object TestButtonsComponent : Component(
        selector = "test-buttons",
        templateUrl = "components/testButtons/testButtons.html",
        controller = ::TestButtonsController
)

class TestButtonsController : Controller() {

    val btn by lookup<HTMLButtonElement>("menuBtn")
    val fetch by lookup<HTMLButtonElement>("fetchBtn")

    val btnSub by lookup<HTMLButtonElement>("subBtn")
    val btnUnsub by lookup<HTMLButtonElement>("unsubBtn")

    val logger by inject<Logger>()

    val poService by inject<HttpTestService>()

    val messageBroker by inject<MessageBroker>()

    override fun onCreate() {
        println("TestButtonsController init")

        fetch.onClick {
            println(poService.hashCode())
            poService.getPublicObject()
        }

        btn.onClick {
            window.alert("clicked!")
            logger.d(this, "btn clicked")
        }

        val listener = { msg: Any ->
            logger.d(this, "got msg: $msg")
        }

        btnSub.onClick {
            messageBroker.subscribe("test", listener)
        }

        btnUnsub.onClick {
            messageBroker.unsubscribe("test", listener)
        }

        findById<HTMLButtonElement>("sendBtn").onclick = {
            messageBroker.publish("test", Date().getTime())
        }
    }

}
