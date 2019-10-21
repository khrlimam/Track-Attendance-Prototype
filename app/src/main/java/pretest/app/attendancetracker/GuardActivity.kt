package pretest.app.attendancetracker

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.auth0.android.provider.WebAuthProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pretest.app.attendancetracker.auth.auth0.Auth0LogoutCallback
import pretest.app.attendancetracker.auth.auth0.Auth0LogoutResult
import pretest.app.attendancetracker.contracts.LogoutResult
import pretest.app.attendancetracker.utils.Statics
import pretest.app.attendancetracker.utils.appPreferences
import pretest.app.attendancetracker.viewmodels.Auth0ProviderFactory
import pretest.app.attendancetracker.viewmodels.AuthViewModel

abstract class GuardActivity : AppCompatActivity() {

  val mAuthViewModel: AuthViewModel by viewModels { Auth0ProviderFactory(this) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    mAuthViewModel.logoutResult.observe(this, observeLogoutResultState())
  }

  fun logout() {
    GlobalScope.launch { mAuthViewModel.logout() }
  }

  private fun observeLogoutResultState() = Observer<LogoutResult> {
    when (val result = it as Auth0LogoutResult) {
      is Auth0LogoutResult.Auth0LogoutSuccess -> {
        appPreferences().edit().remove(Statics.PROFILE_INFO).apply()
        WebAuthProvider.logout(result.auth0)
          .withScheme("demo")
          .start(this, Auth0LogoutCallback(this))
      }
    }
  }

}