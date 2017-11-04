package co.zsmb.weblib.services.navigation

import co.zsmb.weblib.internals.routing.Router

internal object NavigatorImpl : Navigator {

    override fun goto(path: String) {
        Router.navigateTo(path)
    }

}
