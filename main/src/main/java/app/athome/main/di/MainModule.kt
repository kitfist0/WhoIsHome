package app.athome.main.di

import androidx.lifecycle.ViewModel
import app.athome.core.di.ViewModelKey
import app.athome.main.ui.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class MainModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindViewModel(viewModel: MainViewModel): ViewModel
}
