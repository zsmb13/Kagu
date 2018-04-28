package co.zsmb.webmain.components.testButtons

import co.zsmb.kagu.core.Component
import co.zsmb.kagu.core.Controller
import co.zsmb.kagu.core.di.inject
import co.zsmb.kagu.core.dom.onClick
import co.zsmb.kagu.core.findById
import co.zsmb.kagu.core.lookup
import co.zsmb.kagu.services.logging.Logger
import co.zsmb.kagu.services.messaging.MessageBroker
import co.zsmb.kagu.services.navigation.Navigator
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

    val btnNav by lookup<HTMLButtonElement>("navBtn")

    val logger by inject<Logger>()
    val poService by inject<HttpTestService>()
    val messageBroker by inject<MessageBroker>()
    val navigator by inject<Navigator>()

    override fun onCreate() {
        println("TestButtonsController init")

        fetch.onClick {
            println(poService.hashCode())
            poService.performTest()
        }

        btn.onClick {
            logger.d(this, "Alert btn clicked")
            window.alert("clicked!")
        }

        val listener = { msg: Any ->
            logger.d(this, "Got message: $msg")
        }

        btnSub.onClick {
            messageBroker.subscribe("test", listener)
            logger.d(this, "Subscribed")
        }

        btnUnsub.onClick {
            messageBroker.unsubscribe("test", listener)
            logger.d(this, "Unsubscribed")
        }

        btnNav.onClick {
            navigator.goto("/groups/123/user/456")
        }

        findById<HTMLButtonElement>("sendBtn").onclick = {
            messageBroker.publish("test", Date().getTime())
        }
    }

}
