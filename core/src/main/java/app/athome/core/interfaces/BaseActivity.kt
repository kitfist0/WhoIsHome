package app.athome.core.interfaces

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController

abstract class BaseActivity(
    private val navigator: BaseNavigator,
    @LayoutRes layoutId: Int,
    private val navHostId: Int
) : AppCompatActivity(layoutId), NavigatorProvider {

    override fun provideNavigator(): BaseNavigator {
        navigator.navController ?: navigator.bind(findNavController(navHostId))
        return navigator
    }

    override fun onStart() {
        super.onStart()
        navigator.rebind(findNavController(navHostId))
    }

    override fun onStop() {
        super.onStop()
        navigator.unbind()
    }

    override fun onBackPressed() {
        if (navigator.navController?.popBackStack() == false) {
            super.onBackPressed()
        }
    }
}
