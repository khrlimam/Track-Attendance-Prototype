package pretest.app.attendancetracker.auth.auth0

import android.app.Activity
import com.auth0.android.Auth0
import com.auth0.android.authentication.AuthenticationAPIClient
import com.auth0.android.authentication.AuthenticationException
import com.auth0.android.authentication.storage.CredentialsManagerException
import com.auth0.android.authentication.storage.SecureCredentialsManager
import com.auth0.android.authentication.storage.SharedPreferencesStorage
import com.auth0.android.callback.BaseCallback
import com.auth0.android.management.ManagementException
import com.auth0.android.management.UsersAPIClient
import com.auth0.android.provider.WebAuthProvider
import com.auth0.android.result.Credentials
import com.auth0.android.result.UserProfile
import pretest.app.attendancetracker.auth.LoginMethod
import pretest.app.attendancetracker.contracts.AuthenticationProvider
import pretest.app.attendancetracker.contracts.LoginResult
import pretest.app.attendancetracker.contracts.LogoutResult
import pretest.app.attendancetracker.models.Credential
import pretest.app.attendancetracker.models.ProfileInfo
import pretest.app.attendancetracker.utils.Statics
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


class Auth0AuthenticationProvider(
  private val activity: Activity
) : AuthenticationProvider {

  private val mAuth0: Auth0 = Auth0(activity.applicationContext)
  private val authenticationAPIClient = AuthenticationAPIClient(mAuth0)

  private val credentialsManager = SecureCredentialsManager(
    activity,
    AuthenticationAPIClient(mAuth0),
    SharedPreferencesStorage(activity.applicationContext)
  )

  init {
    mAuth0.isOIDCConformant = true
  }

  private fun getUserClient(accessToken: String): UsersAPIClient {
    return UsersAPIClient(mAuth0, accessToken)
  }

  private fun saveCredentials(credentials: Credentials) {
    credentialsManager.saveCredentials(credentials)
  }

  override suspend fun isValidCredential(): Boolean = suspendCoroutine {
    it.resume(credentialsManager.hasValidCredentials())
  }

  override suspend fun login(method: LoginMethod): LoginResult =
    suspendCoroutine<Auth0LoginResult> { continue_ ->
      WebAuthProvider.login(mAuth0)
        .withScheme("demo")
        .withScope("openid profile email offline_access read:current_user update:current_user_metadata")
        .withAudience("https://${Statics.AUTH0_DOMAIN}/api/v2/")
        .start(
          activity, Auth0LoginCallback(
            {
              saveCredentials(it)
              continue_.resume(Auth0LoginResult.Auth0LoginSucceed(it))
            },
            {
              continue_.resume(Auth0LoginResult.Auth0PermissionNotGranted(it))
            },
            {
              continue_.resume(Auth0LoginResult.Auth0LoginFailed(it))
            })
        )
    }


  /**
   * if logoutRequestHandler is null force clear credentials
   */
  override suspend fun logout(): LogoutResult =
    suspendCoroutine { continue_ ->
      credentialsManager.clearCredentials()
      continue_.resume(Auth0LogoutResult.Auth0LogoutSuccess(mAuth0))
    }


  override suspend fun getCredential(): Credential? = suspendCoroutine { continuation ->
    credentialsManager.getCredentials(object :
      BaseCallback<Credentials, CredentialsManagerException> {
      override fun onSuccess(payload: Credentials?) {
        payload?.apply {
          continuation.resume(
            Credential(
              accessToken,
              type,
              idToken,
              refreshToken,
              expiresIn,
              scope,
              expiresAt
            )
          )
        }
      }

      override fun onFailure(error: CredentialsManagerException?) {
        continuation.resume(null)
      }
    })
  }

  override suspend fun getProfile(): ProfileInfo? {
    val accessToken = getCredential()?.accessToken ?: return null
    val userClient = getUserClient(accessToken)
    return suspendCoroutine { continuation ->
      authenticationAPIClient.userInfo(accessToken)
        .start(object : BaseCallback<UserProfile, AuthenticationException> {
          override fun onSuccess(userinfo: UserProfile) {
            userClient
              .getProfile(userinfo.id)
              .start(object : BaseCallback<UserProfile, ManagementException> {

                override fun onSuccess(profile: UserProfile) {
                  continuation.resume(
                    ProfileInfo(
                      profile.id,
                      profile.name,
                      profile.nickname,
                      profile.pictureURL,
                      profile.email,
                      profile.identities.filterNotNull().first().provider
                    )
                  )
                }

                override fun onFailure(error: ManagementException) {
                  continuation.resume(null)
                }
              })
          }

          override fun onFailure(error: AuthenticationException) {
            continuation.resume(null)
          }
        })
    }
  }
}