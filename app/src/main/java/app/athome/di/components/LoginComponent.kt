package app.athome.di.components

import app.athome.di.modules.LoginModule
import app.athome.di.scopes.FragmentScope
import app.athome.ui.login.LoginFragment
import dagger.Component

@FragmentScope
@Component(
    dependencies = [AppComponent::class],
    modules = [LoginModule::class]
)
interface LoginComponent {

    fun inject(fragment: LoginFragment)
}