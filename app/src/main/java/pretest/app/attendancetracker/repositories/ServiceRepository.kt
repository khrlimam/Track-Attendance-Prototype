package pretest.app.attendancetracker.repositories

import pretest.app.attendancetracker.adapters.RecyclerViewWithImageBottomLabelItem.DataHolder
import pretest.app.attendancetracker.models.Service
import pretest.app.attendancetracker.request.endpoints.Services

class ServiceRepository {
  suspend fun getServices(): List<DataHolder> =
    Services.get.all().data.map(Service::toImageWithBottomDataHolder)
}