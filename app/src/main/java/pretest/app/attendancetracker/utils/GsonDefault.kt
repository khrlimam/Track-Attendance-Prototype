package pretest.app.attendancetracker.utils

import com.google.gson.Gson

object GsonDefault {
  val transform by lazy { Gson() }
}
