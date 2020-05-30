package app.athome.main.di

import app.athome.core.di.ViewModelModule
import app.athome.core.interfaces.CoreProvider
import app.athome.main.ui.MainFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [CoreProvider::class],
    modules = [MainModule::class, ViewModelModule::class]
)
interface MainComponent {

    fun inject(fragment: MainFragment)
}