package pretest.app.attendancetracker.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import pretest.app.attendancetracker.adapters.RecyclerViewWithMediaCardItem.DataHolder
import pretest.app.attendancetracker.utils.toDate
import pretest.app.attendancetracker.utils.toLocalDateWithDayName
import pretest.app.attendancetracker.utils.toLocalTime

data class Approval(
  @SerializedName("date")
  @Expose
  val date: String,
  @SerializedName("present")
  @Expose
  val present: Boolean,
  @SerializedName("reason")
  @Expose
  val reason: String
) {
  fun getInisialLabel() = date.toDate()?.toLocalTime() ?: "-"
  fun getTitle() = date.toDate()?.toLocalDateWithDayName() ?: ""
  fun getSubtitle() = if (present) "Present" else "Not Present"

  fun toDataHolder() = DataHolder(getInisialLabel(), getTitle(), getSubtitle(), reason)

}

data class ApprovalsData(
  @SerializedName("data")
  @Expose
  val data: List<Approval>
)