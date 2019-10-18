package pretest.app.attendancetracker.contracts

import pretest.app.attendancetracker.models.Credential
import pretest.app.attendancetracker.models.ProfileInfo

interface AuthenticationProvider {
  suspend fun login(): LoginResult
  suspend fun logout(): LogoutResult
  suspend fun isValidCredential(): Boolean
  suspend fun getCredential(): Credential?
  suspend fun getProfile(): ProfileInfo?
}