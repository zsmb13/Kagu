package co.zsmb.kagu.internals.routing

import co.zsmb.kagu.internals.util.InternalLogger
import kotlin.browser.window

class NoHashPathHandler : PathHandler {

    override fun setPath(path: String) {
        window.history.replaceState("", "this is the new window title", path)
    }

    override fun cleanUrl() {
        // removes "index.html", for example
        val path = window.location.pathname.substringBeforeLast('#')

        InternalLogger.d(this, "path is $path")

        val protocol = window.location.protocol
        val hostname = window.location.hostname
        val port = window.location.port

        val base = if (port.isBlank()) {
            "$protocol//$hostname"
        } else {
            "$protocol//$hostname:$port"
        }

        InternalLogger.d(this, "base is $base")

        val newPath =
                if (path == "index.html") {
                    "$base/"
                } else {
                    val pathWithoutSlashes = path.trim('/')
                    "$base/$pathWithoutSlashes/"
                }

        InternalLogger.d(this, "newPath is $newPath")

        if (path != newPath) {
            setPath(newPath)
        }
    }

    override fun getRoute(): String {
        return window.location.pathname
    }

    override fun setHash(fragment: String) {
        window.history.pushState(data = "some sort of data", title = "new title I guess", url = fragment)
        Router.refresh()
    }

}
