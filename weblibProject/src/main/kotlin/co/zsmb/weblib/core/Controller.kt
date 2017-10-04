package co.zsmb.weblib.core

import org.w3c.dom.Element

abstract class Controller {

    lateinit var root: Element

    open fun onCreate() {}

    open fun onAdded() {}

    open fun onRemoved() {}

    open fun onDestroy() {}

}
