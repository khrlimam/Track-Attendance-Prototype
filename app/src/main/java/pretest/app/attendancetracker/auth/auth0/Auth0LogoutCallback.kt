package pretest.app.attendancetracker.auth.auth0

import android.app.Activity
import android.content.Intent
import com.auth0.android.Auth0Exception
import com.auth0.android.provider.VoidCallback
import pretest.app.attendancetracker.Login

class Auth0LogoutCallback(private val activity: Activity) : VoidCallback {
  override fun onSuccess(payload: Void?) {
    with(activity) {
      startActivity(Intent(activity, Login::class.java))
      finish()
    }
  }

  /**
   *  if logout process errors dont do anything. just let it be
   */
  override fun onFailure(error: Auth0Exception?) {}
}