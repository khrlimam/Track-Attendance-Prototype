package pretest.app.attendancetracker.datasources

import pretest.app.attendancetracker.models.Service
import pretest.app.attendancetracker.request.endpoints.Services

interface ServiceDataSource {
  suspend fun getAllServices(): List<Service>
}

class ServiceNetworkDataSource : ServiceDataSource {
  override suspend fun getAllServices(): List<Service> = Services.get.all().data
}