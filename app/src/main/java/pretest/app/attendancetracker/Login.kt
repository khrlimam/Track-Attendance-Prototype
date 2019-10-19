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
import pretest.app.attendancetracker.Statics.EXTRA_BUNDLE
import pretest.app.attendancetracker.Statics.PROFILE_INFO
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

  private val mAuthViewModel: AuthViewModel by viewModels { Auth0ProviderFactory(this) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_login)
    mAuthViewModel.credential.observe(this, observeCredState())
    mAuthViewModel.profileInfo.observe(this, observeProfileInfoState())
    mAuthViewModel.loginResult.observe(this, observeLoginResultState())
    mAuthViewModel.logoutResult.observe(this, observeLogoutResultState())
  }

  fun login(view: View) {
    GlobalScope.launch { mAuthViewModel.login() }
  }

  fun logout(view: View) {
    GlobalScope.launch { mAuthViewModel.logout() }
  }

  private fun observeCredState() = Observer<Credential?> { credential ->
    credential?.apply {
    }
  }

  private fun observeProfileInfoState() = Observer<ProfileInfo?> {
    it?.apply {
      // redirect user if credential presents. meaning the user is logged in
      startActivity(Intent(this@Login, MainActivity::class.java).apply {
        putExtra(EXTRA_BUNDLE, Bundle().apply {
          // we can use the view model to retrieve user profile info on other activity, but for now just send via intent
          putParcelable(PROFILE_INFO, it)
        })
      })
      finish()
    }
  }

  private fun observeLoginResultState() = Observer<LoginResult> {
    when (val result = it as Auth0LoginResult) {
      is Auth0LoginResult.Auth0LoginFailed -> toast(result.exception.message)
      is Auth0LoginResult.Auth0LoginSucceed -> {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
      }
      is Auth0LoginResult.Auth0PermissionNotGranted -> result.dialog.show()
    }
  }

  private fun observeLogoutResultState() = Observer<LogoutResult> {
    when (val result = it as Auth0LogoutResult) {
      is Auth0LogoutResult.Auth0LogoutSuccess -> WebAuthProvider.logout(result.auth0)
        .withScheme("demo")
        .start(this, Auth0LogoutCallback(this))
    }
  }
}
