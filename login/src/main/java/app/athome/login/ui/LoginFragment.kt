package app.athome.login.ui

import android.content.Intent
import android.view.View
import android.widget.Toast
import app.athome.core.interfaces.BaseFragment
import app.athome.login.R
import app.athome.login.di.LoginComponent
import app.athome.login.nav.LoginNavigation
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseFragment<LoginViewModel>(R.layout.fragment_login) {

    companion object {
        private const val RC_SIGN_IN = 321
    }

    override fun getClassViewModel(): Class<LoginViewModel> = LoginViewModel::class.java

    override fun injectFragment() =
        LoginComponent.getComponent(getCoreProvider()).inject(this)

    override fun onViewModelCreated() {
        buttonLogin.setOnClickListener { viewModel.onSignInClick() }
        // Observe data
        viewModel.isLoadingEvent.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                textTitle.text = getString(R.string.loading)
                buttonLogin.hide()
                buttonPolicy.visibility = View.GONE
            } else {
                textTitle.text = getString(R.string.welcome)
                buttonLogin.show()
                buttonPolicy.visibility = View.VISIBLE
            }
        }
        viewModel.startSignInActivityEvent.observe(viewLifecycleOwner) {
            startActivityForResult(it, RC_SIGN_IN)
        }
        viewModel.successfulLoginEvent.observe(viewLifecycleOwner) {
            getNavigator<LoginNavigation>().fromLoginToMain()
        }
        viewModel.failedLoginEvent.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), getString(R.string.sign_in_failed).format(it), Toast.LENGTH_LONG).show()
        }

        viewModel.checkCurrentUser()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            RC_SIGN_IN -> viewModel.onSignInResult(data)
        }
    }
}
