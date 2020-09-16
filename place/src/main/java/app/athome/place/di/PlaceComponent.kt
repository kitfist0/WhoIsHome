package app.athome.place.di

import app.athome.core.di.ViewModelModule
import app.athome.core.interfaces.CoreProvider
import app.athome.place.ui.PlaceFragment
import dagger.Component

@PlaceScope
@Component(
    dependencies = [CoreProvider::class],
    modules = [PlaceModule::class, ViewModelModule::class]
)
interface PlaceComponent {
    fun inject(fragment: PlaceFragment)

    companion object {
        private var placeComponent: PlaceComponent? = null

        fun getComponent(coreProvider: CoreProvider): PlaceComponent {
            if (placeComponent == null) {
                placeComponent = DaggerPlaceComponent.builder()
                    .coreProvider(coreProvider)
                    .build()
            }
            return requireNotNull(placeComponent)
        }
    }
}
