package app.athome.di.components

import app.athome.di.modules.MainModule
import app.athome.di.scopes.FragmentScope
import app.athome.ui.main.MainFragment
import dagger.Component

@FragmentScope
@Component(
    dependencies = [AppComponent::class],
    modules = [MainModule::class]
)
interface MainComponent {

    fun inject(fragment: MainFragment)
}