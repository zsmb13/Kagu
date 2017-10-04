package co.zsmb.weblib.core

abstract class Component(val selector: String,
                         val templateUrl: String,
                         val controller: () -> Controller)
