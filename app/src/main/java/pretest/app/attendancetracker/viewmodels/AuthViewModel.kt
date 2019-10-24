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

  private val _credential: MutableLiveData<Credential?> by lazy { MutableLiveData<Credential?>() }
  val credential: LiveData<Credential?> = _credential

  private val _profileInfo: MutableLiveData<ProfileInfo?> by lazy { MutableLiveData<ProfileInfo?>() }
  val profileInfo: LiveData<ProfileInfo?> = _profileInfo

  private val _validCredential: LiveData<Boolean> by lazy { liveData { emit(isValidCredential()) } }
  val validCredential: LiveData<Boolean> = _validCredential

  private val _loginResult: MutableLiveData<LoginResult> by lazy { MutableLiveData<LoginResult>() }
  val loginResult: LiveData<LoginResult> = _loginResult

  private val _logoutResult: MutableLiveData<LogoutResult> by lazy { MutableLiveData<LogoutResult>() }
  val logoutResult: LiveData<LogoutResult> = _logoutResult

  suspend fun login() {
    _loginResult.postValue(provider.login())
  }

  suspend fun logout() {
    _logoutResult.postValue(provider.logout())
  }

  private suspend fun getCredential(): Credential? {
    return provider.getCredential()
  }

  private suspend fun getProfile(): ProfileInfo? {
    return provider.getProfile()
  }

  suspend fun requestProfileInfo() {
    _profileInfo.postValue(getProfile())
  }

  suspend fun requestUserCredential() {
    _credential.postValue(getCredential())
  }

  private suspend fun isValidCredential(): Boolean {
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