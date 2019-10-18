package pretest.app.attendancetracker.contracts

import androidx.appcompat.app.AppCompatActivity
import pretest.app.attendancetracker.models.Credential
import pretest.app.attendancetracker.models.ProfileInfo

interface AuthenticationProvider {
    fun login()
    fun logout()
    fun isValidCredential(): Boolean
    suspend fun getCredential(): Credential?
    suspend fun getProfile(): ProfileInfo?
}