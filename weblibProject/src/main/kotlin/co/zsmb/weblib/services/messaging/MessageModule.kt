package co.zsmb.weblib.services.messaging

import co.zsmb.koinjs.dsl.module.Module

object MessageModule : Module() {

    override fun context() = declareContext {
        provide { createMessageBroker() }
    }

    fun createMessageBroker(): MessageBroker = MessageBrokerImpl

}
