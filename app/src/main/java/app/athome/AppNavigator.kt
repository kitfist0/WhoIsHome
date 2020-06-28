package app.athome

import android.os.Bundle
import app.athome.core.interfaces.BaseNavigator
import app.athome.login.nav.LoginNavigation
import app.athome.main.nav.MainNavigation

class AppNavigator : BaseNavigator(), LoginNavigation, MainNavigation {

    // LoginFragment Navigation
    override fun fromLoginToMain() {
        navController?.navigate(R.id.action_login_to_main)
    }

    // MainFragment Navigation
    override fun fromMainToPlace(placeId: Long) {
        val bundle = Bundle()
        bundle.putLong("placeId", placeId)
        navController?.navigate(R.id.action_main_to_place, bundle)
    }
}