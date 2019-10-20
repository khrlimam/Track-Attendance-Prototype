package pretest.app.attendancetracker.request.endpoints

import pretest.app.attendancetracker.models.ServicesData
import pretest.app.attendancetracker.request.client
import retrofit2.http.GET

interface Services {
  /**
   * See {@linkplain https://attendancetrackermockdata.docs.apiary.io/#reference/0/service-collections/list-all-services}
   */
  @GET("/services")
  suspend fun all(): ServicesData

  companion object {
    val get by lazy { client.create(Services::class.java) }
  }

}