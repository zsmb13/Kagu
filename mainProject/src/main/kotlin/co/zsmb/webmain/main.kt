package co.zsmb.webmain

import co.zsmb.example.external.button.ButtonComponent
import co.zsmb.weblib.dsl.application
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
            path = "/user"
            handler = UserComponent
        }
        state {
            path = "/secondButton"
            handler = ButtonComponent
        }
        defaultState {
            path = "/button"
            handler = ButtonComponent
        }
    }

    afterInit {
        println("Inited.")
    }

}
