package co.zsmb.webmain.components.links

import co.zsmb.weblib.core.Component
import co.zsmb.weblib.core.Controller

object LinksComponent : Component(
        selector = "links-component",
        templateUrl = "components/links/links.html",
        controller = ::LinksController
)

class LinksController : Controller() {

    override fun onCreate() {
        println("LinksController init")

    }

}
