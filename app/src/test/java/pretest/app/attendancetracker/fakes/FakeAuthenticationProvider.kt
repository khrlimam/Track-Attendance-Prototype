package pretest.app.attendancetracker.fakes

import pretest.app.attendancetracker.auth.LoginMethod
import pretest.app.attendancetracker.contracts.AuthenticationProvider
import pretest.app.attendancetracker.contracts.LoginResult
import pretest.app.attendancetracker.contracts.LogoutResult
import pretest.app.attendancetracker.models.Credential
import pretest.app.attendancetracker.models.ProfileInfo
import java.util.*

class FakeAuthenticationProvider : AuthenticationProvider {

  companion object {
    const val USER1 = "user1"
    const val USER2 = "user2"

    val credential = mapOf(
      USER1 to Credential(
        "12345token", "auth", "idtoken", "refreshtoken", 123456L, "profile", Date()
      ),
      USER2 to Credential(
        "12345token", "auth", "idtoken", "refreshtoken", 123456L, "profile", Date()
      )
    )

    val profileInfos = mapOf(
      USER1 to ProfileInfo(
        "user1",
        "User 1",
        "User 1",
        "http://google.com/user1",
        "user1@domain.com",
        "Fake"
      ),
      USER2 to ProfileInfo(
        "user2",
        "User 2",
        "User 2",
        "http://google.com/user2",
        "user2@domain.com",
        "Fake"
      )
    )
  }

  private lateinit var username: String

  object Auth {
    private val users = listOf(
      mapOf("username" to USER1, "password" to "user1", "access" to "write"),
      mapOf("username" to USER2, "password" to "user2", "access" to "read")
    )

    fun login(username: String, password: String): Boolean {
      val found = users.filter {
        it["username"].equals(username).and(it["password"].equals(password))
      }
      return found.size == 1
    }

    fun getUser(username: String) = users.find { it["username"] == username }

  }


  override suspend fun login(method: LoginMethod): LoginResult {
    method as LoginMethod.Legacy
    return if (Auth.login(method.identifier, method.password)) {
      // simulate saving credential
      username = method.identifier
      FakeLoginResult.LoginSuccess
    } else FakeLoginResult.LoginFailed
  }

  override suspend fun logout(): LogoutResult {
    username = "" //simulating deleting credential
    return FakeLogoutResult.LogoutSuccess
  }

  // just returns true to simulate validation credential
  override suspend fun isValidCredential(): Boolean = credential[username] is Credential

  override suspend fun getCredential(): Credential? = credential[username]

  override suspend fun getProfile(): ProfileInfo? = profileInfos[username]
}

sealed class FakeLoginResult : LoginResult() {
  object LoginSuccess : FakeLoginResult()
  object LoginFailed : FakeLoginResult()
}

sealed class FakeLogoutResult : LogoutResult() {
  object LogoutSuccess : FakeLogoutResult()
}