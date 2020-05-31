package app.athome.login.ui

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import app.athome.core.interfaces.CoreProvider
import app.athome.core.interfaces.CoreFragment
import app.athome.login.R
import app.athome.login.di.DaggerLoginComponent
import kotlinx.android.synthetic.main.fragment_login.view.*
import javax.inject.Inject

class LoginFragment(
    private val onLoginSuccess: (() -> Unit)
): CoreFragment(R.layout.fragment_login) {

    companion object {
        fun newInstance(onLoginSuccess: (() -> Unit)) = LoginFragment(onLoginSuccess)
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: LoginViewModel

    override fun injectFragment(coreProvider: CoreProvider) {
        DaggerLoginComponent.builder().coreProvider(coreProvider).build().inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.buttonLogin.setOnClickListener { onLoginSuccess.invoke() }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory).get(LoginViewModel::class.java)
    }
}
