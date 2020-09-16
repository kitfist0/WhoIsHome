package app.athome.place.di

import androidx.lifecycle.ViewModel
import app.athome.core.di.ViewModelKey
import app.athome.place.ui.PlaceViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class PlaceModule {

    @Binds
    @IntoMap
    @ViewModelKey(PlaceViewModel::class)
    abstract fun bindViewModel(viewModel: PlaceViewModel): ViewModel
}
