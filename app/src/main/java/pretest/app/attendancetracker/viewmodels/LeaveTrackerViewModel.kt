package pretest.app.attendancetracker.viewmodels

import android.os.Bundle
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import kotlinx.coroutines.delay
import pretest.app.attendancetracker.datasources.LeaveTrackerNetworkDataSource
import pretest.app.attendancetracker.models.LeaveTracker
import pretest.app.attendancetracker.repositories.LeaveTrackerRepository
import pretest.app.attendancetracker.request.BaseErrorState
import pretest.app.attendancetracker.request.RequestState

class LeaveTrackerViewModel(
  private val leaveTrackerRepository: LeaveTrackerRepository
) : ViewModel() {

  constructor(
    savedStateHandle: SavedStateHandle,
    leaveTrackerRepository: LeaveTrackerRepository
  ) : this(leaveTrackerRepository)


  private val _requestState: MutableLiveData<RequestState> by lazy { MutableLiveData<RequestState>() }
  val requestState: LiveData<RequestState> = _requestState

  private val _leaveTracker: MutableLiveData<LeaveTracker> by lazy { MutableLiveData<LeaveTracker>() }
  val leaveTracker: LiveData<LeaveTracker> = _leaveTracker

  suspend fun getAllLeaveTrackers() {
    try {
      _requestState.postValue(RequestState.StateLoading(true))
      val leaveTrackers = leaveTrackerRepository.getAllLeaveTracker()
      _requestState.postValue(RequestState.StateLoading(false))
      simulateAttendanceTracking(leaveTrackers, 5000)
    } catch (e: Exception) {
      _requestState.postValue(BaseErrorState(e))
    }
  }

  private suspend fun simulateAttendanceTracking(data: List<LeaveTracker>, ms: Long) {
    data.forEach {
      delay(ms)
      _leaveTracker.postValue(it)
    }
  }

}

@Suppress("UNCHECKED_CAST")
class LeaveTrackerViewModelFactory(owner: SavedStateRegistryOwner, args: Bundle?) :
  AbstractSavedStateViewModelFactory(owner, args) {

  private val leaveTrackerNetworkDataSource: LeaveTrackerNetworkDataSource by lazy { LeaveTrackerNetworkDataSource() }
  private val leaveTrackerRepository: LeaveTrackerRepository by lazy {
    LeaveTrackerRepository(
      leaveTrackerNetworkDataSource
    )
  }

  override fun <T : ViewModel?> create(
    key: String,
    modelClass: Class<T>,
    handle: SavedStateHandle
  ): T = LeaveTrackerViewModel(handle, leaveTrackerRepository) as T
}