package pretest.app.attendancetracker.auth.auth0

import android.app.Dialog
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.provider.AuthCallback
import com.auth0.android.result.Credentials

class Auth0LoginCallback(
  val onSuccess_: (Credentials) -> Unit,
  val onPermissionNotGranted: (Dialog) -> Unit,
  val onLoginFailed: (AuthenticationException) -> Unit
) : AuthCallback {
  override fun onSuccess(credentials: Credentials) {
    onSuccess_(credentials)
  }

  override fun onFailure(dialog: Dialog) {
    onPermissionNotGranted(dialog)
  }

  override fun onFailure(exception: AuthenticationException?) {
    exception?.let { onLoginFailed(it) }
  }
}