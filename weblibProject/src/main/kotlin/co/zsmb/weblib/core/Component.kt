package co.zsmb.weblib.core

import co.zsmb.weblib.core.controller.Controller

abstract class Component(val selector: String,
                         val templateUrl: String,
                         val controller: () -> Controller)
