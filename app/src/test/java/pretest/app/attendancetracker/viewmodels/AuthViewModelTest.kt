package pretest.app.attendancetracker.viewmodels

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import pretest.app.attendancetracker.CoroutineBaseRuleTest
import pretest.app.attendancetracker.auth.LoginMethod
import pretest.app.attendancetracker.fakes.FakeAuthenticationProvider
import pretest.app.attendancetracker.fakes.FakeAuthenticationProvider.Companion.DEFAULT_USER
import pretest.app.attendancetracker.fakes.FakeAuthenticationProvider.Companion.USER1
import pretest.app.attendancetracker.fakes.FakeLoginResult
import pretest.app.attendancetracker.fakes.FakeLogoutResult
import pretest.app.attendancetracker.utils.getOrAwaitValue
import pretest.app.attendancetracker.utils.observeOnce

class AuthViewModelTest : CoroutineBaseRuleTest() {

  private lateinit var authViewModel: AuthViewModel
  private lateinit var authProvider: FakeAuthenticationProvider

  @Before
  fun setUp() {
    authProvider = FakeAuthenticationProvider()
    authViewModel = AuthViewModel(authProvider)
  }

  @Test
  fun `when given valid credential login must succeed`() {
    authViewModel.loginResult.observeOnce {
      Assert.assertEquals(it, FakeLoginResult.LoginSuccess)
    }
    authViewModel.login(LoginMethod.Legacy(USER1, USER1))
  }

  @Test
  fun `when given invalid credential login must failed`() {
    authViewModel.loginResult.observeOnce {
      Assert.assertEquals(it, FakeLoginResult.LoginFailed)
    }
    authViewModel.login(LoginMethod.Legacy("invalid", "invalid"))
  }

  @Test
  fun `when login is succeed auth provider must returns valid credential data`() {
    authViewModel.login(LoginMethod.Legacy(USER1, USER1))
    authViewModel.requestUserCredential()
    val credential = authViewModel.credential.getOrAwaitValue()
    Assert.assertEquals(credential, FakeAuthenticationProvider.credential[USER1])
  }

  @Test
  fun `when login is succeed auth provider must returns valid profileInfo data`() {
    authViewModel.login(LoginMethod.Legacy(USER1, USER1))
    authViewModel.requestProfileInfo()
    val profileInfo = authViewModel.profileInfo.getOrAwaitValue()
    Assert.assertEquals(profileInfo, FakeAuthenticationProvider.profileInfos[USER1])
  }

  @Test
  fun `when login is succeed credential returned must valid`() {
    authViewModel.login(LoginMethod.Legacy(USER1, USER1))
    authViewModel.validCredential.observeOnce {
      assert(it)
    }
  }

  @Test
  fun `when third party login succeed make sure valid credential is retrieved`() {
    authViewModel.login(LoginMethod.ThirdParty)
    authViewModel.loginResult.observeOnce {
      when (it) {
        is FakeLoginResult.LoginSuccess -> {
          authViewModel.requestUserCredential()
          authViewModel.credential.observeOnce {
            Assert.assertEquals(it, FakeAuthenticationProvider.credential[DEFAULT_USER])
          }
        }
      }
    }
  }

  @Test
  fun logout() {
    authViewModel.logout()
    val logoutResult = authViewModel.logoutResult.getOrAwaitValue()
    Assert.assertEquals(logoutResult, FakeLogoutResult.LogoutSuccess)
  }
}