package co.zsmb.kagu.services.http

typealias Header = Pair<String, String>

interface HttpService {

    fun get(url: String,
            headers: List<Header> = listOf(),
            onSuccess: (String) -> Unit,
            onError: (String) -> Unit)

    fun post(url: String,
             body: Any,
             headers: List<Header> = listOf(),
             onSuccess: (String) -> Unit,
             onError: (String) -> Unit)

    fun put(url: String,
            body: Any,
            headers: List<Header> = listOf(),
            onSuccess: (String) -> Unit,
            onError: (String) -> Unit)

    fun delete(url: String,
               headers: List<Header> = listOf(),
               onSuccess: (String) -> Unit,
               onError: (String) -> Unit)

    var backoffStrategy: BackoffStrategy

    enum class BackoffStrategy {
        None,
        Linear,
        Exponential
    }

}
