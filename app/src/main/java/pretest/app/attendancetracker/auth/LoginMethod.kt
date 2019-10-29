package pretest.app.attendancetracker.auth

sealed class LoginMethod {
  data class Legacy(val identifier: String, val password: String) : LoginMethod()
  object ThirdParty : LoginMethod()
}