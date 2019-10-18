package pretest.app.attendancetracker.contracts

import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.result.Credentials

interface HandleAuth0LoginRespone {
    fun onLoginFailed(exception: AuthenticationException)
    fun onLoginSucceed(credentials: Credentials)
}