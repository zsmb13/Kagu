package co.zsmb.weblib.core.controller

import org.w3c.dom.Element

abstract class Controller {

    lateinit var root: Element

    open fun onInit() {}

}
