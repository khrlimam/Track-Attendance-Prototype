package pretest.app.attendancetracker.viewmodels

import android.os.Bundle
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import pretest.app.attendancetracker.adapters.RecyclerViewWithImageBottomLabelItem.DataHolder
import pretest.app.attendancetracker.repositories.ServiceRepository

class ServicesViewModel(
  private val serviceRepository: ServiceRepository
) : ViewModel() {

  constructor(
    savedStateHandle: SavedStateHandle,
    serviceRepository: ServiceRepository
  ) : this(serviceRepository)

  val servicesMenu: LiveData<List<DataHolder>> by lazy {
    liveData { emit(serviceRepository.getServices()) }
  }
}

@Suppress("UNCHECKED_CAST")
class ServiceViewModelFactory(
  owner: SavedStateRegistryOwner,
  defaultArgs: Bundle?
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

  private val repository: ServiceRepository by lazy { ServiceRepository() }

  override fun <T : ViewModel?> create(
    key: String,
    modelClass: Class<T>,
    handle: SavedStateHandle
  ): T = ServicesViewModel(handle, repository) as T
}