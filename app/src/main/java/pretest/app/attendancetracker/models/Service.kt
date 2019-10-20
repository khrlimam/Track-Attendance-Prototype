package pretest.app.attendancetracker.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Service(
  @SerializedName("name")
  @Expose
  val name: String,
  @SerializedName("icon")
  @Expose
  val icon: String
)

data class ServicesData(
  @SerializedName("data")
  @Expose
  val data: List<Service>
)