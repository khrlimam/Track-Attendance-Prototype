package pretest.app.attendancetracker.viewmodels

import android.os.Bundle
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import pretest.app.attendancetracker.adapters.RecyclerViewWithImageBottomLabelItem.DataHolder
import pretest.app.attendancetracker.datasources.ServiceNetworkDataSource
import pretest.app.attendancetracker.repositories.ServiceRepository
import pretest.app.attendancetracker.request.BaseErrorState
import pretest.app.attendancetracker.request.RequestState

class ServicesViewModel(
  private val serviceRepository: ServiceRepository
) : ViewModel() {

  constructor(
    savedStateHandle: SavedStateHandle,
    serviceRepository: ServiceRepository
  ) : this(serviceRepository)

  private val _requestState: MutableLiveData<RequestState> by lazy { MutableLiveData<RequestState>() }
  val requestState: LiveData<RequestState> = _requestState

  val servicesMenu: LiveData<List<DataHolder>> by lazy {
    liveData {
      try {
        _requestState.postValue(RequestState.StateLoading(true))
        emit(serviceRepository.getServices())
        _requestState.postValue(RequestState.StateLoading(false))
      } catch (e: Exception) {
        _requestState.postValue(BaseErrorState(e))
      }
    }
  }
}

@Suppress("UNCHECKED_CAST")
class ServiceViewModelFactory(
  owner: SavedStateRegistryOwner,
  defaultArgs: Bundle?
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {

  private val servicesNetworkDataSource: ServiceNetworkDataSource by lazy { ServiceNetworkDataSource() }
  private val repository: ServiceRepository by lazy { ServiceRepository(servicesNetworkDataSource) }

  override fun <T : ViewModel?> create(
    key: String,
    modelClass: Class<T>,
    handle: SavedStateHandle
  ): T = ServicesViewModel(handle, repository) as T
}