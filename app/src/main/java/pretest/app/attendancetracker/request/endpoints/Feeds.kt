package pretest.app.attendancetracker.request.endpoints

import pretest.app.attendancetracker.models.FeedsData
import pretest.app.attendancetracker.request.client
import retrofit2.http.GET

interface Feeds {
  @GET("/feeds")
  suspend fun all(): FeedsData

  companion object {
    val get: Feeds by lazy { client.create(Feeds::class.java) }
  }

}