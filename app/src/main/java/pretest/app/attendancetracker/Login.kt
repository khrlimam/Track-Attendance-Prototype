package pretest.app.attendancetracker

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_login.*
import pretest.app.attendancetracker.auth.LoginMethod
import pretest.app.attendancetracker.auth.auth0.Auth0LoginResult
import pretest.app.attendancetracker.contracts.LoginResult
import pretest.app.attendancetracker.models.ProfileInfo
import pretest.app.attendancetracker.utils.GsonDefault
import pretest.app.attendancetracker.utils.Statics.PROFILE_INFO
import pretest.app.attendancetracker.utils.appPreferences
import pretest.app.attendancetracker.utils.toast
import pretest.app.attendancetracker.utils.userInfoSerialized


class Login : GuardActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)
    mAuthViewModel.profileInfo.observe(this, observeProfileInfoState())
    mAuthViewModel.loginResult.observe(this, observeLoginResultState())
    mAuthViewModel.validCredential.observe(this, observeValidCredential())
    btnLogin.setOnClickListener(login())
    btnSignup.setOnClickListener(login())
  }

  private fun observeValidCredential(): Observer<in Boolean> = Observer { isValid ->
    if (isValid) {
      // redirect user if credential valids. meaning the user is logged in
      redirectToMainActivity()
    } else toast("Your session is no longer valid. Please login")
  }

  private fun login(): (View) -> Unit = {
    mAuthViewModel.login(LoginMethod.ThirdParty)
  }

  private fun observeProfileInfoState() = Observer<ProfileInfo?> {
    it?.apply {
      appPreferences().edit().putString(PROFILE_INFO, GsonDefault.transform.toJson(this)).apply()
      redirectToMainActivity()
    }
  }

  private fun redirectToMainActivity() {
    if (userInfoSerialized().isEmpty()) mAuthViewModel.requestProfileInfo()
    else {
      startActivity(Intent(this, MainActivity::class.java))
      finish()
    }
  }

  private fun observeLoginResultState() = Observer<LoginResult> {
    when (val result = it as Auth0LoginResult) {
      is Auth0LoginResult.Auth0LoginFailed -> toast(result.exception.message)
      is Auth0LoginResult.Auth0LoginSucceed -> mAuthViewModel.requestProfileInfo()
      is Auth0LoginResult.Auth0PermissionNotGranted -> result.dialog.show()
    }
  }
}
