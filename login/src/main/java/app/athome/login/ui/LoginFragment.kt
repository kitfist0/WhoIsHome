package app.athome.login.ui

import android.os.Bundle
import android.view.View
import app.athome.core.interfaces.BaseFragment
import app.athome.login.R
import app.athome.login.di.LoginComponent
import app.athome.login.nav.LoginNavigation
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseFragment<LoginViewModel>(R.layout.fragment_login) {

    override fun getClassViewModel(): Class<LoginViewModel> = LoginViewModel::class.java

    override fun injectFragment() =
        LoginComponent.getComponent(getCoreProvider()).inject(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buttonLogin.setOnClickListener {
            (getNavigator<LoginNavigation>()).fromLoginToMain()
        }
    }

    override fun onViewModelCreated() {
        // ...
    }
}
