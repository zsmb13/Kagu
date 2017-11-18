package co.zsmb.kagu.services.templates

import org.w3c.dom.HTMLElement

interface TemplateLoader {

    fun get(url: String, callback: (HTMLElement) -> Unit)

}
