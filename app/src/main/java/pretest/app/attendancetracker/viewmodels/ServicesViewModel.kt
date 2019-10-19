package pretest.app.attendancetracker.viewmodels

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import pretest.app.attendancetracker.RecyclerviewWithImageAndLabelBottomItem.DataHolder
import pretest.app.attendancetracker.repositories.ServiceRepository

class ServicesViewModel(
  val savedStateHandle: SavedStateHandle,
  val serviceRepository: ServiceRepository
) : ViewModel() {

  val servicesMenu: LiveData<List<DataHolder>> by lazy {
    liveData { emit(serviceRepository.getServices().await()) }
  }
}

@Suppress("UNCHECKED_CAST")
class ServiceViewModelFactory(
  owner: SavedStateRegistryOwner,
  defaultArgs: Bundle?,
  context: Context?
) :
  AbstractSavedStateViewModelFactory(owner, defaultArgs) {

  private val repository: ServiceRepository by lazy { ServiceRepository(context) }

  override fun <T : ViewModel?> create(
    key: String,
    modelClass: Class<T>,
    handle: SavedStateHandle
  ): T {
    return ServicesViewModel(handle, repository) as T
  }
}