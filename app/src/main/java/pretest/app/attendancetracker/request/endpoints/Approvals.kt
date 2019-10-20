package pretest.app.attendancetracker.request.endpoints

import pretest.app.attendancetracker.models.ApprovalsData
import pretest.app.attendancetracker.request.client
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Approvals {

  /**
   * See {@linkplain https://github.com/khrlimam/attendance-tracker-mock-data/blob/master/apiary.apib#L1260}
   */
  @GET("/user/{username}/approval")
  suspend fun from(@Path("username") username: String, @Query("status") status: String): ApprovalsData

  companion object {
    val get by lazy { client.create(Approvals::class.java) }
  }
}