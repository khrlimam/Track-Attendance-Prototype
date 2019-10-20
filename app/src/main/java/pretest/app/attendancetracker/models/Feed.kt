package pretest.app.attendancetracker.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Feed(
  @SerializedName("date")
  @Expose
  val date: String,
  @SerializedName("name")
  @Expose
  val name: String,
  @SerializedName("present")
  @Expose
  val present: Boolean,
  @SerializedName("reason")
  @Expose
  val reason: String
) {
  fun getInisialName(): String {
    val namArray = name.split(" ")
    return "${namArray.first()}${namArray.last()}"
  }
}

data class FeedsData(
  @SerializedName("data")
  @Expose
  val data: List<Feed>
)