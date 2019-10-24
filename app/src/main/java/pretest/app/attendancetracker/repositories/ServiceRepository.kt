package pretest.app.attendancetracker.repositories

import pretest.app.attendancetracker.adapters.RecyclerViewWithImageBottomLabelItem.DataHolder
import pretest.app.attendancetracker.datasources.ServiceDataSource
import pretest.app.attendancetracker.models.Service

class ServiceRepository(private val dataSource: ServiceDataSource) {
  suspend fun getServices(): List<DataHolder> =
    dataSource.getAllServices().map(Service::toImageWithBottomDataHolder)
}