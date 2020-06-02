package app.athome

import app.athome.core.interfaces.BaseNavigator
import app.athome.login.nav.LoginNavigation

class AppNavigator : BaseNavigator(), LoginNavigation {

    // LoginFragment Navigation
    override fun fromLoginToMain() {
        navController?.navigate(R.id.mainFragment)
    }
}