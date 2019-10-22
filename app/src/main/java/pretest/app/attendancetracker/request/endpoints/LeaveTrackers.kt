package pretest.app.attendancetracker.request.endpoints

import pretest.app.attendancetracker.models.LeaveTrackersData
import pretest.app.attendancetracker.request.client
import retrofit2.http.GET

interface LeaveTrackers {
  @GET("/leave-trackers")
  suspend fun all(): LeaveTrackersData

  companion object {
    val get: LeaveTrackers by lazy { client.create(LeaveTrackers::class.java) }
  }

}