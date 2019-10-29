package pretest.app.attendancetracker.fakes

import pretest.app.attendancetracker.contracts.AuthenticationProvider
import pretest.app.attendancetracker.contracts.LoginResult
import pretest.app.attendancetracker.contracts.LogoutResult
import pretest.app.attendancetracker.models.Credential
import pretest.app.attendancetracker.models.ProfileInfo

class FakeAuthenticationProvider:AuthenticationProvider {
  override suspend fun login(): LoginResult {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override suspend fun logout(): LogoutResult {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override suspend fun isValidCredential(): Boolean {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override suspend fun getCredential(): Credential? {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }

  override suspend fun getProfile(): ProfileInfo? {
    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
  }
}