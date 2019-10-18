package pretest.app.attendancetracker.viewmodels

import android.app.Activity
import androidx.lifecycle.*
import pretest.app.attendancetracker.auth.auth0.Auth0AuthenticationProvider
import pretest.app.attendancetracker.contracts.AuthenticationProvider
import pretest.app.attendancetracker.contracts.LoginResult
import pretest.app.attendancetracker.contracts.LogoutResult
import pretest.app.attendancetracker.models.Credential
import pretest.app.attendancetracker.models.ProfileInfo

class AuthViewModel(private val provider: AuthenticationProvider) : ViewModel() {

  val credential: LiveData<Credential?> by lazy { liveData { emit(getCredential()) } }
  val profileInfo: LiveData<ProfileInfo?> by lazy { liveData { emit(getProfile()) } }
  val loginResult: MutableLiveData<LoginResult> by lazy { MutableLiveData<LoginResult>() }
  val logoutResult: MutableLiveData<LogoutResult> by lazy { MutableLiveData<LogoutResult>() }

  suspend fun login() {
    loginResult.postValue(provider.login())
  }

  suspend fun logout() {
    logoutResult.postValue(provider.logout())
  }

  private suspend fun getCredential(): Credential? {
    return provider.getCredential()
  }

  private suspend fun getProfile(): ProfileInfo? {
    return provider.getProfile()
  }

  suspend fun isValidCredential(): Boolean {
    return provider.isValidCredential()
  }

}

class Auth0ProviderFactory(private val activity: Activity) : ViewModelProvider.Factory {

  private val auth0LoginProvider: Auth0AuthenticationProvider by lazy {
    Auth0AuthenticationProvider(activity)
  }

  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    @Suppress("UNCHECKED_CAST")
    return AuthViewModel(auth0LoginProvider) as T
  }
}