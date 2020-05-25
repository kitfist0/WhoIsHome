package app.athome

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.athome.di.components.*

internal fun Fragment.appComponent() : AppComponent {
    return (requireActivity().applicationContext as App).appComponent
}

internal fun Fragment.mainComponent(): MainComponent {
    return DaggerMainComponent.builder().appComponent(appComponent()).build()
}

internal fun Fragment.loginComponent(): LoginComponent {
    return DaggerLoginComponent.builder().appComponent(appComponent()).build()
}

inline fun <reified T : ViewModel> Fragment.injectViewModel(factory: ViewModelProvider.Factory): T {
    return ViewModelProvider(viewModelStore, factory)[T::class.java]
}