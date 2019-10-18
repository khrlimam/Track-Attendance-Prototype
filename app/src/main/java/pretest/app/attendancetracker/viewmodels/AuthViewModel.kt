package pretest.app.attendancetracker.viewmodels

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pretest.app.attendancetracker.auth.auth0.Auth0AuthenticationProvider
import pretest.app.attendancetracker.contracts.AuthenticationProvider
import pretest.app.attendancetracker.contracts.HandleAuth0LoginRespone
import pretest.app.attendancetracker.contracts.HandleAuth0LogoutRequest
import pretest.app.attendancetracker.models.Credential
import pretest.app.attendancetracker.models.ProfileInfo

class AuthViewModel(private val provider: AuthenticationProvider) : ViewModel() {

    val credential: LiveData<Credential?> = MutableLiveData<Credential>().apply {
        GlobalScope.launch { postValue(getCredential()) }
    }

    val profileInfo: LiveData<ProfileInfo?> = MutableLiveData<ProfileInfo>().apply {
        GlobalScope.launch { postValue(getProfile()) }
    }

    fun login() {
        provider.login()
    }

    fun logout() {
        provider.logout()
    }

    private suspend fun getCredential(): Credential? {
        return provider.getCredential()
    }

    private suspend fun getProfile(): ProfileInfo? {
        return provider.getProfile()
    }

    fun isValidCredential(): Boolean {
        return provider.isValidCredential()
    }

}

class Auth0ProviderFactory(
    private val activity: Activity,
    private val loginResponeHandler: HandleAuth0LoginRespone? = null,
    private val logoutRequestHandler: HandleAuth0LogoutRequest? = null
) : ViewModelProvider.NewInstanceFactory() {

    private val auth0LoginProvider: Auth0AuthenticationProvider by lazy {
        Auth0AuthenticationProvider(
            activity,
            loginResponeHandler,
            logoutRequestHandler
        )
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return AuthViewModel(auth0LoginProvider) as T
    }
}