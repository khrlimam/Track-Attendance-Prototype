package pretest.app.attendancetracker.viewmodels

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import pretest.app.attendancetracker.adapters.RecyclerViewWithImageBottomLabelItem.DataHolder
import pretest.app.attendancetracker.repositories.ServiceRepository

class ServicesViewModel(
  val savedStateHandle: SavedStateHandle,
  private val serviceRepository: ServiceRepository
) : ViewModel() {

  val servicesMenu: LiveData<List<DataHolder>> by lazy {
    liveData { emit(serviceRepository.getServices().data.map { DataHolder(it.name, it.icon) }) }
  }
}

@Suppress("UNCHECKED_CAST")
class ServiceViewModelFactory(
  owner: SavedStateRegistryOwner,
  defaultArgs: Bundle?,
  context: Context?
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

  private val repository: ServiceRepository by lazy { ServiceRepository() }

  override fun <T : ViewModel?> create(
    key: String,
    modelClass: Class<T>,
    handle: SavedStateHandle
  ): T = ServicesViewModel(handle, repository) as T
}