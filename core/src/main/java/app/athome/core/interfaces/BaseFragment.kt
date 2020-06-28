package app.athome.core.interfaces

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

abstract class BaseFragment<VM : ViewModel, NI>(layoutId: Int) : Fragment(layoutId) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var viewModel: VM
    private val coreProvider: CoreProvider by lazy {
        (requireContext().applicationContext as BaseApplication).getCoreProvider()
    }

    protected abstract fun getClassViewModel(): Class<VM>
    protected abstract fun getNavInterface(): Class<NI>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectFragment(coreProvider)
    }

    protected abstract fun injectFragment(coreProvider: CoreProvider)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(viewModelStore, viewModelFactory).get(getClassViewModel())
        onViewModelCreated()
    }

    protected abstract fun onViewModelCreated()

    fun getNavigator(): NI {
        @Suppress("UNCHECKED_CAST")
        return (requireActivity() as NavigatorProvider).provideNavigator() as NI
    }
}