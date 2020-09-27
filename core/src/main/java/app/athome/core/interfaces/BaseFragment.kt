package app.athome.core.interfaces

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

abstract class BaseFragment<VM : ViewModel>(layoutId: Int) : Fragment(layoutId) {

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun <T>Fragment.getNavigator(): T =
            (requireActivity() as NavigatorProvider).provideNavigator() as T

        fun Fragment.getCoreProvider() =
            (requireContext().applicationContext as BaseApplication).getCoreProvider()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: VM

    protected abstract fun getClassViewModel(): Class<VM>

    override fun onAttach(context: Context) {
        injectFragment()
        super.onAttach(context)
    }

    protected abstract fun injectFragment()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory).get(getClassViewModel())
        onViewModelCreated()
    }

    protected abstract fun onViewModelCreated()
}
