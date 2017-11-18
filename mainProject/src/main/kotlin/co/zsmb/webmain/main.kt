package co.zsmb.webmain

import co.zsmb.example.external.button.ButtonComponent
import co.zsmb.webmain.components.links.LinksComponent
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
        +LinksComponent
        +ButtonComponent
        +TestButtonsComponent
    }

    routing {
        state {
            path = "/groups/:groupId/user/:userId"
            handler = UserComponent
        }
        state {
            path = "/secondButton"
            handler = ButtonComponent
        }
        state {
            path = "/button"
            handler = ButtonComponent
        }
        state {
            path = "/cssbuttons"
            handler = TestButtonsComponent
        }
        defaultState {
            path = "/links"
            handler = LinksComponent
        }
    }

    afterInit {
        println("Inited.")
    }

}
