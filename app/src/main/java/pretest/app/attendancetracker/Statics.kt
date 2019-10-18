package pretest.app.attendancetracker

import android.content.Context
import android.widget.Toast

object Statics {
    const val EXTRA_CLEAR_CREDENTIALS = "com.auth0.CLEAR_CREDENTIALS"
    const val EXTRA_ACCESS_TOKEN = "com.auth0.ACCESS_TOKEN"
    const val AUTH0_DOMAIN = "mantap.au.auth0.com"
}

fun Context.toast(message: String?) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}