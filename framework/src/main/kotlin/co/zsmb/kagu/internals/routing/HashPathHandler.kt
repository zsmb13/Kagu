package co.zsmb.kagu.internals.routing

import kotlin.browser.window

class HashPathHandler : PathHandler {

    init {
        window.onhashchange = {
            Router.refresh()
        }
    }

    override fun getRoute(): String = window.location.hash.substring(1)

    override fun setHash(fragment: String) {
        window.location.hash = fragment
        Router.refresh()
    }

    override fun setPath(path: String) {
        window.history.replaceState("", "this is the new window title", path)
    }

    override fun cleanUrl() {
        // removes "index.html", for example
        val href = window.location.href

        val hash = href
                .substringAfterLast('#', missingDelimiterValue = "")
                .trim('/')

        val base = href
                .substringBeforeLast('#')
                .dropLastWhile { it != '/' }

        val newHref = if (hash.isBlank()) {
            "$base#/"
        } else {
            "$base#/$hash/"
        }

        if (href != newHref) {
            setPath(newHref)
        }
    }

}
