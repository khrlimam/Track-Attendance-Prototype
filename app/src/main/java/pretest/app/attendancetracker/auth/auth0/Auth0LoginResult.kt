package pretest.app.attendancetracker.auth.auth0

import android.app.Dialog
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.result.Credentials
import pretest.app.attendancetracker.contracts.LoginResult

sealed class Auth0LoginResult : LoginResult() {
  class Auth0LoginSucceed(val credentials: Credentials) : Auth0LoginResult()
  class Auth0PermissionNotGranted(val dialog: Dialog) : Auth0LoginResult()
  class Auth0LoginFailed(val exception: AuthenticationException) : Auth0LoginResult()
}