package co.zsmb.kagu.internals.jquery

import org.w3c.dom.Element
import kotlin.browser.document

internal object JQ {

    fun parseHTML(html: String): Array<Any>
            = jQuery.parseHTML(html, document)

    fun select(selector: String, context: JQuery? = undefined): JQuery
            = jQuery(selector, context)

    fun select(element: Element): JQuery = jQuery(element)

}
