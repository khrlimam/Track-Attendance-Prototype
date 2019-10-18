package pretest.app.attendancetracker.auth

import pretest.app.attendancetracker.contracts.Auth

class Auth0Source(val loginCalbback: LoginCalbback) : Auth {
    override fun login() {
        loginCalbback.showLoginDialog()
    }

    override fun logout() {
        loginCalbback.doLogout()
    }
}

interface LoginCalbback {
    fun showLoginDialog()
    fun doLogout()
}