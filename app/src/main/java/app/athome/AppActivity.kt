package app.athome

import app.athome.core.interfaces.BaseActivity

class AppActivity : BaseActivity<AppNavigator>(R.layout.activity, R.id.fragmentContainerView) {

    override fun getClassNavigator(): AppNavigator = AppNavigator()
}
