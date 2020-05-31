package app.athome

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import app.athome.login.ui.LoginFragment
import app.athome.main.ui.MainFragment

class AppActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity)
        openLogin()
    }

    private fun openLogin() {
        supportFragmentManager.beginTransaction().replace(
            R.id.fragmentContainerView,
            LoginFragment.newInstance(onLoginSuccess = { openMain() })
        ).commitNow()
    }

    private fun openMain() {
        supportFragmentManager.beginTransaction().replace(
            R.id.fragmentContainerView,
            MainFragment.newInstance(
                onPlaceClicked = { openPlace(it) },
                onAddClicked = {}
            )
        ).commitNow()
    }

    private fun openPlace(placeId: Long) {
        Toast.makeText(this, "Open place $placeId", Toast.LENGTH_SHORT).show()
    }
}
