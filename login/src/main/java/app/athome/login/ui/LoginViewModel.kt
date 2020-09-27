package app.athome.login.ui

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import app.athome.core.interfaces.BaseApplication
import app.athome.core.util.SingleLiveEvent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    application: Application,
    private val firebaseAuth: FirebaseAuth
) : AndroidViewModel(application) {

    companion object {
        private const val TAG = "LOGIN_VIEW_MODEL"
        private const val FAILED_MESSAGE = "Google sign in failed"

        private fun getCredential(idToken: String) =
            GoogleAuthProvider.getCredential(idToken, null)

        private fun getSignInIntent(context: Context, options: GoogleSignInOptions) =
            GoogleSignIn.getClient(context, options).signInIntent

        private fun getSignInOptions(application: Application) =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken((application as BaseApplication).getClientId())
                .requestEmail()
                .build()
    }

    val isLoadingEvent: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val startSignInActivityEvent: SingleLiveEvent<Intent> = SingleLiveEvent()
    val successfulLoginEvent: SingleLiveEvent<FirebaseUser> = SingleLiveEvent()
    val failedLoginEvent: SingleLiveEvent<String> = SingleLiveEvent()

    init {
        isLoadingEvent.value = true
        firebaseAuth.currentUser?.let { loginSuccess(it) } ?: isLoadingEvent.setValue(false)
    }

    fun checkCurrentUser() {}

    fun onSignInClick() {
        isLoadingEvent.value = true
        startSignInActivityEvent.value = getSignInIntent(
            getApplication(),
            getSignInOptions(getApplication())
        )
    }

    fun onSignInResult(data: Intent?) {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        try {
            val account = task.getResult(ApiException::class.java)!!
            firebaseAuthWithGoogle(account.idToken!!)
        } catch (e: ApiException) {
            loginFailed(e.toString())
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) =
        firebaseAuth.signInWithCredential(getCredential(idToken))
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    loginSuccess(firebaseAuth.currentUser)
                } else {
                    loginFailed(task.exception.toString())
                }
            }

    private fun loginFailed(message: String?) {
        failedLoginEvent.value = message ?: FAILED_MESSAGE
        isLoadingEvent.value = false
    }

    private fun loginSuccess(firebaseUser: FirebaseUser?) {
        successfulLoginEvent.value = firebaseUser
        isLoadingEvent.value = false
    }
}
