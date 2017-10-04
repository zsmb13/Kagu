package co.zsmb.weblib.services.messaging

typealias MessageCallback = (Any) -> Unit

interface MessageBroker {

    fun subscribe(channelName: String, callback: MessageCallback): Boolean
    fun unsubscribe(channelName: String, callback: MessageCallback): Boolean
    fun publish(channelName: String, message: Any)

}
