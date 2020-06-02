package app.athome.main.di

import app.athome.core.di.ViewModelModule
import app.athome.core.interfaces.CoreProvider
import app.athome.main.ui.MainFragment
import dagger.Component

@MainScope
@Component(
    dependencies = [CoreProvider::class],
    modules = [MainModule::class, ViewModelModule::class]
)
interface MainComponent {

    fun inject(fragment: MainFragment)

    companion object {
        private var mainComponent: MainComponent? = null

        fun getComponent(coreProvider: CoreProvider): MainComponent {
            if (mainComponent == null) {
                mainComponent = DaggerMainComponent.builder()
                    .coreProvider(coreProvider)
                    .build()
            }
            return requireNotNull(mainComponent)
        }
    }
}