package app.athome.login.di

import app.athome.core.di.ViewModelModule
import app.athome.core.interfaces.CoreProvider
import app.athome.login.ui.LoginFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [CoreProvider::class],
    modules = [LoginModule::class, ViewModelModule::class]
)
interface LoginComponent {

    fun inject(fragment: LoginFragment)
}