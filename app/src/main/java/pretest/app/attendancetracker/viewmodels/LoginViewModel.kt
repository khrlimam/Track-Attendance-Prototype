package pretest.app.attendancetracker.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pretest.app.attendancetracker.contracts.Auth

class LoginViewModel(val auth: Auth) : ViewModel() {
    val token = MutableLiveData<String>().apply {
        value = ""
    }

    fun login() {
        auth.login()
    }

    fun logout() {
        auth.logout()
    }

}
