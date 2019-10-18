package pretest.app.attendancetracker.auth.auth0

import android.app.Activity
import android.content.Intent
import com.auth0.android.Auth0Exception
import com.auth0.android.provider.VoidCallback
import pretest.app.attendancetracker.Login

class Auth0LogoutCallback(private val activity: Activity, val onLogout: () -> Unit) : VoidCallback {
    override fun onSuccess(payload: Void?) {
        onLogout()
        with(activity) {
            startActivity(Intent(activity, Login::class.java))
            finish()
        }
    }

    override fun onFailure(error: Auth0Exception?) {

    }
}