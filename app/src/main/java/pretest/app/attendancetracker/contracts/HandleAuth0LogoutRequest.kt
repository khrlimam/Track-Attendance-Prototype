package pretest.app.attendancetracker.contracts

import com.auth0.android.Auth0

interface HandleAuth0LogoutRequest {
    fun doLogout(auth0: Auth0, onLogoutSuccess: () -> Unit)
}