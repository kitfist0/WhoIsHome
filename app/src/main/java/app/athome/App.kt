package app.athome

import app.athome.core.interfaces.BaseApplication

@Suppress("unused")
class App: BaseApplication() {

    override fun getClientId() = getString(R.string.default_web_client_id)
}
