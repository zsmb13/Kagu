package co.zsmb.kagu.internals.di

import co.zsmb.koinjs.log.Logger

object NoOpLogger : Logger {
    override fun err(msg: String) {
    }

    override fun log(msg: String) {
    }

    override fun debug(msg: String) {
    }
}
