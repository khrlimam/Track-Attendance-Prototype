package pretest.app.attendancetracker.utils

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.navOptions
import com.bumptech.glide.Glide
import pretest.app.attendancetracker.BuildConfig
import pretest.app.attendancetracker.R
import pretest.app.attendancetracker.models.ProfileInfo

object Statics {
  const val AUTH0_DOMAIN = "mantap.au.auth0.com"
  const val PROFILE_INFO = "PROFILE_INFO"

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

fun Context.appPreferences() =
  getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)

fun Context.toast(message: String?) {
  Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun ImageView.loadFromUrl(url: String) {
  Glide.with(context).load(url).into(this)
}

fun Context.getUserInfo(): ProfileInfo {
  return GsonDefault.transform.fromJson(
    appPreferences().getString(Statics.PROFILE_INFO, ""),
    ProfileInfo::class.java
  )
}