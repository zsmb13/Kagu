package co.zsmb.kagu.services.messaging


internal object MessageBrokerImpl : MessageBroker {

    private val subscriptions = mutableMapOf<String, MutableSet<MessageCallback>>()

    private fun getChannel(channelName: String): MutableSet<MessageCallback> {
        if (!subscriptions.containsKey(channelName)) {
            subscriptions[channelName] = mutableSetOf()
        }

        return subscriptions[channelName]!!
    }

    override fun subscribe(channelName: String, callback: MessageCallback) =
            getChannel(channelName).add(callback)

    override fun unsubscribe(channelName: String, callback: MessageCallback) =
            getChannel(channelName).remove(callback)

    override fun publish(channelName: String, message: Any) =
            getChannel(channelName).forEach { it(message) }

}
