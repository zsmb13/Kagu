package co.zsmb.kagu.services.messaging

import co.zsmb.koinjs.dsl.module.Module

internal object MessageModule : Module() {

    override fun context() = declareContext {
        provide { MessageBrokerImpl as MessageBroker }
    }

}
