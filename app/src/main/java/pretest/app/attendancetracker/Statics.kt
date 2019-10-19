package pretest.app.attendancetracker

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.navOptions
import com.bumptech.glide.Glide

object Statics {
  const val EXTRA_CLEAR_CREDENTIALS = "com.auth0.CLEAR_CREDENTIALS"
  const val EXTRA_ACCESS_TOKEN = "com.auth0.ACCESS_TOKEN"
  const val AUTH0_DOMAIN = "mantap.au.auth0.com"
  const val PROFILE_INFO = "PROFILE_INFO"
  const val EXTRA_BUNDLE = "EXTRA_BUNDLE"


  val fadeInFadeOutTransition by lazy {
    navOptions {
      anim {
        enter = R.anim.fade_in
        exit = R.anim.fade_out
        popEnter = R.anim.fade_in
        popExit = R.anim.fade_out
      }
    }
  }
}

fun Context.toast(message: String?) {
  Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun ImageView.loadFromUrl(url: String) {
  Glide.with(context).load(url).into(this)
}