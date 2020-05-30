package app.athome.login.ui

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    companion object {
        private const val tag = "LOGIN_VIEW_MODEL"
    }
}
