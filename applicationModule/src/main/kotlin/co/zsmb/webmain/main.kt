package co.zsmb.webmain

import co.zsmb.example.external.button.ButtonComponent
import co.zsmb.kagu.core.init.application
import co.zsmb.webmain.components.menu.MenuComponent
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
    }

    afterInit {
        println("Inited.")
    }

}
