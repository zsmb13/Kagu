package co.zsmb.weblib.services.http

typealias Header = Pair<String, String>

interface HttpService {

    fun get(url: String,
            headers: List<Header> = listOf(),
            callback: (String) -> Unit)

    fun post(url: String,
             body: Any,
             headers: List<Header> = listOf(),
             callback: (String) -> Unit)

}
