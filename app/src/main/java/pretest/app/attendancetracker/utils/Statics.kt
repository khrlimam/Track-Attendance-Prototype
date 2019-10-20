package pretest.app.attendancetracker.utils

import androidx.navigation.navOptions
import pretest.app.attendancetracker.R

object Statics {
  const val AUTH0_DOMAIN = "mantap.au.auth0.com"
  const val PROFILE_INFO = "PROFILE_INFO"
  const val MOCK_USERNAME = "khairulimam"

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