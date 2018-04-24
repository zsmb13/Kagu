package co.zsmb.kagu.services.messaging

import co.zsmb.koinjs.dsl.module.applicationContext

val MessageModule = applicationContext {
    bean { MessageBrokerImpl as MessageBroker }
}
