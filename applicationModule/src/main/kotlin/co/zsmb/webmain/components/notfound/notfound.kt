package co.zsmb.webmain.components.notfound

import co.zsmb.kagu.core.Component
import co.zsmb.kagu.core.Controller

object NotFoundComponent : Component(
        selector = "not-found",
        templateUrl = "components/notfound/notfound.html",
        controller = ::NotFoundController
)

class NotFoundController : Controller()
