package pretest.app.attendancetracker.models

import java.util.*

data class MainActivityNavigationState(
    var bottomMenuSelected: String = "Services",
    var pageTitle: String = "Services",
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

data class ProfileInfo(
    val id: String? = "",
    val name: String? = "",
    val nickName: String? = "",
    val pictureUrl: String? = "",
    val email: String? = "",
    val provider: String? = "",
    val additionalInfo: Map<String, String>? = null
)