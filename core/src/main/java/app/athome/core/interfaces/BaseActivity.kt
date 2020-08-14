package app.athome.core.interfaces

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController

abstract class BaseActivity(
    private val navigator: BaseNavigator,
    layoutId: Int,
    private val navHostId: Int
) : AppCompatActivity(layoutId), NavigatorProvider {

    private val navController by lazy {
        findNavController(navHostId)
    }

    override fun provideNavigator(): BaseNavigator {
        return navigator
    }

    override fun onResume() {
        super.onResume()
        navigator.bind(navController)
    }

    override fun onPause() {
        super.onPause()
        navigator.unbind()
    }

    override fun onBackPressed() {
        if (!navController.popBackStack()) {
            super.onBackPressed()
        }
    }
}