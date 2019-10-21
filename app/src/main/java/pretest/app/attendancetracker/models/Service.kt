package pretest.app.attendancetracker.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import pretest.app.attendancetracker.adapters.RecyclerViewWithImageBottomLabelItem

data class Service(
  @SerializedName("name")
  @Expose
  val name: String,
  @SerializedName("icon")
  @Expose
  val icon: String
) {
  fun toImageWithBottomDataHolder() = RecyclerViewWithImageBottomLabelItem.DataHolder(name, icon)
}

data class ServicesData(
  @SerializedName("data")
  @Expose
  val data: List<Service>
)