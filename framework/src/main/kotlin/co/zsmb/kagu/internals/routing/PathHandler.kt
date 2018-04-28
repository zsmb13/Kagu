package co.zsmb.kagu.internals.routing

interface PathHandler {
    fun setPath(path: String)
    fun cleanUrl()
    fun getRoute(): String
    fun setHash(fragment: String)
}

