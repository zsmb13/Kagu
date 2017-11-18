package co.zsmb.kagu.services.navigation

import co.zsmb.kagu.internals.routing.Router

internal object NavigatorImpl : Navigator {

    override fun goto(path: String) {
        Router.navigateTo(path)
    }

}
