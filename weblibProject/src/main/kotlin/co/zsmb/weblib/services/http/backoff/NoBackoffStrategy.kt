package co.zsmb.weblib.services.http.backoff

class NoBackoffStrategy : BackoffStrategy {

    override fun onSuccess() {}

    override fun onError() {}

    override fun allowsCalls() = true

}
