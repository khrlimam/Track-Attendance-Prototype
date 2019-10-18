package pretest.app.attendancetracker.auth.auth0

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.result.Credentials
import pretest.app.attendancetracker.MainActivity
import pretest.app.attendancetracker.Statics
import pretest.app.attendancetracker.contracts.HandleAuth0LoginRespone

class Auth0LoginResponseHandler(val activity: Activity) : HandleAuth0LoginRespone {
    override fun onLoginFailed(exception: AuthenticationException) {
        with(activity) {
            Toast.makeText(this, "Error: " + exception.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onLoginSucceed(credentials: Credentials) {
        with(activity) {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(Statics.EXTRA_ACCESS_TOKEN, credentials.accessToken)
            startActivity(intent)
            finish()
        }
    }
}