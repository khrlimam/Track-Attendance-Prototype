package pretest.app.attendancetracker

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.auth0.android.provider.WebAuthProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pretest.app.attendancetracker.auth.auth0.Auth0LoginResult
import pretest.app.attendancetracker.auth.auth0.Auth0LogoutCallback
import pretest.app.attendancetracker.auth.auth0.Auth0LogoutResult
import pretest.app.attendancetracker.contracts.LoginResult
import pretest.app.attendancetracker.contracts.LogoutResult
import pretest.app.attendancetracker.models.Credential
import pretest.app.attendancetracker.models.ProfileInfo
import pretest.app.attendancetracker.viewmodels.Auth0ProviderFactory
import pretest.app.attendancetracker.viewmodels.AuthViewModel


class Login : AppCompatActivity() {

  private val mViewModel: AuthViewModel by viewModels { Auth0ProviderFactory(this) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)
    mViewModel.credential.observe(this, handleCredential())
    mViewModel.profileInfo.observe(this, handleProfileInfo())
    mViewModel.loginResult.observe(this, handleLoginResult())
    mViewModel.logoutResult.observe(this, handleLogoutResult())
  }

  fun login(view: View) {
    GlobalScope.launch { mViewModel.login() }
  }

  fun logout(view: View) {
    GlobalScope.launch { mViewModel.logout() }
  }

  fun handleCredential() = Observer<Credential?> {
    it?.let { Log.i(localClassName, it.toString()) }
  }

  fun handleProfileInfo() = Observer<ProfileInfo?> {
    it?.let { Log.i(localClassName, it.toString()) }
  }

  fun handleLoginResult() = Observer<LoginResult> {
    when (val result = it as Auth0LoginResult) {
      is Auth0LoginResult.Auth0LoginFailed -> toast(result.exception.message)
      is Auth0LoginResult.Auth0LoginSucceed -> {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
      }
      is Auth0LoginResult.Auth0PermissionNotGranted -> result.dialog.show()
    }
  }

  fun handleLogoutResult() = Observer<LogoutResult> {
    when (val result = it as Auth0LogoutResult) {
      is Auth0LogoutResult.Auth0LogoutSuccess -> WebAuthProvider.logout(result.auth0)
        .withScheme("demo")
        .start(this, Auth0LogoutCallback(this))
    }
  }

}
