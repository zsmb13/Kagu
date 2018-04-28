package co.zsmb.webmain

import co.zsmb.example.external.button.ButtonComponent
import co.zsmb.kagu.core.init.application
import co.zsmb.webmain.components.menu.MenuComponent
import co.zsmb.webmain.components.notfound.NotFoundComponent
import co.zsmb.webmain.components.testButtons.TestButtonsComponent
import co.zsmb.webmain.components.user.UserComponent
import co.zsmb.webmain.modules.MyModule

fun main(args: Array<String>) = application {

    modules {
        +MyModule
    }

    components {
        +UserComponent
        +MenuComponent
        +ButtonComponent
        +TestButtonsComponent
    }

    routing {
        config {
            noHashMode = false
        }
        state {
            path = "/groups/:groupId/user/:userId"
            handler = UserComponent
        }
        defaultState {
            path = "/button1"
            handler = ButtonComponent
        }
        state {
            path = "/button2"
            handler = ButtonComponent
        }
        state {
            path = "/testButtons"
            handler = TestButtonsComponent
        }
        state {
            path = "/404"
            handler = NotFoundComponent
        }
    }

    afterInit {
        println("Inited.")
    }

}
