package pretest.app.attendancetracker

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.auth0.android.provider.WebAuthProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pretest.app.attendancetracker.Statics.PROFILE_INFO
import pretest.app.attendancetracker.auth.auth0.Auth0LoginResult
import pretest.app.attendancetracker.auth.auth0.Auth0LogoutCallback
import pretest.app.attendancetracker.auth.auth0.Auth0LogoutResult
import pretest.app.attendancetracker.contracts.LoginResult
import pretest.app.attendancetracker.contracts.LogoutResult
import pretest.app.attendancetracker.models.ProfileInfo
import pretest.app.attendancetracker.utils.gson
import pretest.app.attendancetracker.viewmodels.Auth0ProviderFactory
import pretest.app.attendancetracker.viewmodels.AuthViewModel


class Login : AppCompatActivity() {

  private val mAuthViewModel: AuthViewModel by viewModels { Auth0ProviderFactory(this) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)
    mAuthViewModel.profileInfo.observe(this, observeProfileInfoState())
    mAuthViewModel.loginResult.observe(this, observeLoginResultState())
    mAuthViewModel.logoutResult.observe(this, observeLogoutResultState())
    mAuthViewModel.validCredential.observe(this, observeValidCredential())
  }

  private fun observeValidCredential(): Observer<in Boolean> = Observer { isValid ->
    if (isValid) {
      // redirect user if credential valids. meaning the user is logged in
      redirectToMainActivity()
    } else toast("Your session is no longer valid. Please login")
  }

  fun login(view: View) {
    GlobalScope.launch { mAuthViewModel.login() }
  }

  fun logout(view: View) {
    GlobalScope.launch { mAuthViewModel.logout() }
  }

  private fun observeProfileInfoState() = Observer<ProfileInfo?> {
    it?.apply {
      appPreferences().edit().putString(PROFILE_INFO, gson.toJson(this)).apply()
      redirectToMainActivity()
    }
  }

  private fun redirectToMainActivity() {
    startActivity(Intent(this, MainActivity::class.java))
    finish()
  }

  private fun observeLoginResultState() = Observer<LoginResult> {
    when (val result = it as Auth0LoginResult) {
      is Auth0LoginResult.Auth0LoginFailed -> toast(result.exception.message)
      is Auth0LoginResult.Auth0LoginSucceed -> {
        GlobalScope.launch { mAuthViewModel.requestProfileInfo() }
      }
      is Auth0LoginResult.Auth0PermissionNotGranted -> result.dialog.show()
    }
  }

  private fun observeLogoutResultState() = Observer<LogoutResult> {
    when (val result = it as Auth0LogoutResult) {
      is Auth0LogoutResult.Auth0LogoutSuccess -> {
        appPreferences().edit().remove(PROFILE_INFO).apply()
        WebAuthProvider.logout(result.auth0)
          .withScheme("demo")
          .start(this, Auth0LogoutCallback(this))
      }
    }
  }
}
