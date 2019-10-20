package pretest.app.attendancetracker.repositories

import pretest.app.attendancetracker.models.ServicesData
import pretest.app.attendancetracker.request.endpoints.Services

class ServiceRepository {
  suspend fun getServices(): ServicesData = Services.get.all()
}