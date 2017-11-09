package co.zsmb.webmain.components.links

import co.zsmb.weblib.core.Component
import co.zsmb.weblib.core.Controller
import co.zsmb.weblib.core.di.inject
import co.zsmb.weblib.services.logging.Logger
import co.zsmb.weblib.services.storage.CookieStorage
import co.zsmb.weblib.services.storage.LocalStorage

object LinksComponent : Component(
        selector = "links-component",
        templateUrl = "components/links/links.html",
        controller = ::LinksController
)

class LinksController : Controller() {

    val cookieStorage by inject<CookieStorage>()
    val localStorage by inject<LocalStorage>()

    val logger by inject<Logger>()

    override fun onCreate() {
        logger.d(this, "Created")

        logger.d(this, "Cookie is: ${cookieStorage["cookieKey"]}")
        logger.d(this, "Cookie is: ${cookieStorage["cookieKey2"]}")
        logger.d(this, "Cookie is: ${cookieStorage["cookieKey3"]}")
        cookieStorage["cookieKey"] = "cookieValue!"
        cookieStorage["cookieKey2"] = "cookieValue!2222"
        cookieStorage["cookieKey3"] = "cookieValue3333!"
        logger.d(this, "Placed cookie cookieKey = cookieValue!")
        logger.d(this, "Placed cookie cookieKey2 = cookieValue!2222")
        logger.d(this, "Placed cookie cookieKey3 = cookieValue3333!")
        logger.d(this, "Cookie is: ${cookieStorage["cookieKey"]}")
        logger.d(this, "Cookie is: ${cookieStorage["cookieKey2"]}")
        logger.d(this, "Cookie is: ${cookieStorage["cookieKey3"]}")

        logger.d(this, "Local storage value is: ${localStorage["storageKey"]}")
        logger.d(this, "Local storage value is: ${localStorage["storageKey2"]}")
        logger.d(this, "Local storage value is: ${localStorage["storageKey3"]}")
        localStorage["storageKey"] = "storage value !!4"
        localStorage["storageKey2"] = "storage value !!4222222"
        localStorage["storageKey3"] = "storage value !333!4"
        logger.d(this, "Placed into localstorage storageKey = storage value !!4")
        logger.d(this, "Placed into localstorage storageKey2 = storage value !!4222222")
        logger.d(this, "Placed into localstorage storageKey3 = storage value !333!4")
        logger.d(this, "Local storage value is: ${localStorage["storageKey"]}")
        logger.d(this, "Local storage value is: ${localStorage["storageKey2"]}")
        logger.d(this, "Local storage value is: ${localStorage["storageKey3"]}")

    }

}
