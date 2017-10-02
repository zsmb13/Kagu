package co.zsmb.weblib.messaging

typealias MessageCallback = (Any) -> Unit

object MessageBroker {

    private val subscriptions = mutableMapOf<String, MutableSet<MessageCallback>>()

    private fun getChannel(channelName: String): MutableSet<MessageCallback> {
        if (!subscriptions.containsKey(channelName)) {
            subscriptions[channelName] = mutableSetOf()
        }

        return subscriptions[channelName]!!
    }

    fun subscribe(channelName: String, callback: MessageCallback) =
            getChannel(channelName).add(callback)

    fun unsubscribe(channelName: String, callback: MessageCallback) =
            getChannel(channelName).remove(callback)

    fun publish(channelName: String, message: Any) =
            getChannel(channelName).forEach { it(message) }

}
