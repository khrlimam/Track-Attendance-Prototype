package pretest.app.attendancetracker.models

import android.os.Parcelable
import androidx.fragment.app.Fragment
import kotlinx.android.parcel.Parcelize
import java.util.*

data class MainActivityNavigationState(
  var bottomMenuSelected: String = "ServicesData",
  var pageTitle: String = "ServicesData",
  var menuPosition: Int = 0
)


data class Credential(
  val accessToken: String? = "",
  val tokenType: String? = "",
  val idToken: String? = "",
  val refreshToken: String? = "",
  val expiresIn: Long? = 0L,
  val scope: String? = "",
  val expiresAt: Date? = Date(),
  val additionalInfo: Map<String, String>? = null
)

@Parcelize
data class ProfileInfo(
  val id: String? = "",
  val name: String? = "",
  val nickName: String? = "",
  val pictureUrl: String? = "",
  val email: String? = "",
  val provider: String? = "",
  val additionalInfo: Map<String, String>? = null
) : Parcelable

data class FragmentPage(val title: String, val fragment: Fragment)