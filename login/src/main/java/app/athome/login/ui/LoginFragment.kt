package app.athome.login.ui

import android.os.Bundle
import android.view.View
import app.athome.core.interfaces.CoreProvider
import app.athome.core.interfaces.BaseFragment
import app.athome.login.R
import app.athome.login.di.LoginComponent
import app.athome.login.nav.LoginNavigation
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseFragment<LoginViewModel, LoginNavigation>(R.layout.fragment_login) {

    override fun getClassViewModel(): Class<LoginViewModel> = LoginViewModel::class.java
    override fun getNavInterface(): Class<LoginNavigation> = LoginNavigation::class.java

    override fun injectFragment(coreProvider: CoreProvider) =
        LoginComponent.getComponent(coreProvider).inject(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonLogin.setOnClickListener { getNavigator().fromLoginToMain() }
    }

    override fun onViewModelCreated() {
        // ...
    }
}
