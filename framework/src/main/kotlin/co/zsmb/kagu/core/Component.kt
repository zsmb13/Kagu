package co.zsmb.kagu.core

abstract class Component(val selector: String,
                         val templateUrl: String,
                         val controller: () -> Controller)
