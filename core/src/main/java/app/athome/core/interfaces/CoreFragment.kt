package app.athome.core.interfaces

import android.os.Bundle
import androidx.fragment.app.Fragment

abstract class CoreFragment(layoutId: Int) : Fragment(layoutId) {

    private val coreProvider: CoreProvider by lazy {
        (requireContext().applicationContext as CoreApplication).getCoreProvider()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectFragment(coreProvider)
    }

    open fun injectFragment(coreProvider: CoreProvider) {
        // ...
    }
}