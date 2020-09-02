package app.athome.login.di

import app.athome.core.di.ViewModelModule
import app.athome.core.interfaces.CoreProvider
import app.athome.login.ui.LoginFragment
import dagger.Component

@LoginScope
@Component(
    dependencies = [CoreProvider::class],
    modules = [LoginModule::class, ViewModelModule::class]
)
interface LoginComponent {

    fun inject(fragment: LoginFragment)

    companion object {
        private var loginComponent: LoginComponent? = null

        fun getComponent(coreProvider: CoreProvider): LoginComponent {
            if (loginComponent == null) {
                loginComponent = DaggerLoginComponent.builder()
                    .coreProvider(coreProvider)
                    .build()
            }
            return requireNotNull(loginComponent)
        }
    }
}
