package co.zsmb.weblib.services.messaging

import co.zsmb.koinjs.dsl.module.Module

internal object MessageModule : Module() {

    override fun context() = declareContext {
        provide { createMessageBroker() }
    }

    fun createMessageBroker(): MessageBroker = MessageBrokerImpl

}
