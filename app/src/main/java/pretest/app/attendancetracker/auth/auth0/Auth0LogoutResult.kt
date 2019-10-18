package pretest.app.attendancetracker.auth.auth0

import com.auth0.android.Auth0
import pretest.app.attendancetracker.contracts.LogoutResult

sealed class Auth0LogoutResult : LogoutResult() {
  class Auth0LogoutSuccess(val auth0: Auth0) : Auth0LogoutResult()
}