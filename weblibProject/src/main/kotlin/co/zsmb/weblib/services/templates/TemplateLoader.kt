package co.zsmb.weblib.services.templates

import org.w3c.dom.Element

interface TemplateLoader {

    fun get(url: String, callback: (Element) -> Unit)

}
