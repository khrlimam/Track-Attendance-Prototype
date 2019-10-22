package pretest.app.attendancetracker.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LeaveTracker(
  @SerializedName("ad_id")
  @Expose
  val adId: String,
  @SerializedName("ad_id_type")
  @Expose
  val adIdType: String,
  @SerializedName("milsec_utc_timestamp")
  @Expose
  val milsecUtcTimestamp: Double,
  @SerializedName("lat")
  @Expose
  val lat: Double,
  @SerializedName("long")
  @Expose
  val _long: Double,
  @SerializedName("gps_hr_accuracy")
  @Expose
  val gpsHrAccuracy: Double,
  @SerializedName("ip_add")
  @Expose
  val ipAdd: String,
  @SerializedName("country")
  @Expose
  val country: String,
  @SerializedName("device_os_language_code")
  @Expose
  val deviceOsLanguageCode: String,
  @SerializedName("app_id")
  @Expose
  val appId: String,
  @SerializedName("present")
  @Expose
  val present: Boolean,
  @SerializedName("reason")
  @Expose
  val reason: String,
  @SerializedName("name")
  @Expose
  val name: String
)

data class LeaveTrackersData(
  @SerializedName("data")
  @Expose
  val data: List<LeaveTracker>
)