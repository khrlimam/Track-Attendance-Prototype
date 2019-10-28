package pretest.app.attendancetracker.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import pretest.app.attendancetracker.BuildConfig
import pretest.app.attendancetracker.models.ProfileInfo
import java.text.SimpleDateFormat
import java.util.*

fun View.gone() {
  visibility = View.GONE
}

fun View.visible() {
  visibility = View.VISIBLE
}

fun Context.appPreferences(): SharedPreferences =
  getSharedPreferences(BuildConfig.APPLICATION_ID, Context.MODE_PRIVATE)

fun Context.toast(message: String?) {
  Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun ImageView.setImageFrom(url: String) {
  Glide.with(context).load(url).into(this)
}

fun Context.userInfoSerialized(): String =
  appPreferences().getString(Statics.PROFILE_INFO, "") ?: ""

fun Context.getUserInfo(): ProfileInfo {
  return GsonDefault.transform.fromJson(userInfoSerialized(), ProfileInfo::class.java)
}

@SuppressLint("SimpleDateFormat")
fun String.toDate(): Date? {
  val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
  formatter.timeZone = TimeZone.getTimeZone("Asia/Makassar")
  return formatter.parse(this)
}

@SuppressLint("SimpleDateFormat")
fun Date.toLocalDateWithDayName(): String = SimpleDateFormat("EEE, dd MMM yyy").format(this)

@SuppressLint("SimpleDateFormat")
fun Date.toLocalTime(): String = SimpleDateFormat("HH:mm").format(this)

@SuppressLint("DefaultLocale")
fun String.titleCase() = this.split(" ").map { it.capitalize() }.joinToString(" ")


inline fun <reified T> Any.cast(): T = if (this !is T)
  throw ClassCastException("${this::class.java.name} cannot be cast to ${T::class.java.name}")
else this