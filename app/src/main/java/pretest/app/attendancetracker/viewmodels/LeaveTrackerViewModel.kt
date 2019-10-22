package pretest.app.attendancetracker.viewmodels

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import kotlinx.coroutines.delay
import pretest.app.attendancetracker.models.LeaveTracker
import pretest.app.attendancetracker.repositories.LeaveTrackerRepository

class LeaveTrackerViewModel(
  val savedStateHandle: SavedStateHandle,
  private val leaveTrackerRepository: LeaveTrackerRepository
) : ViewModel() {
  val leaveTracker: MutableLiveData<LeaveTracker> by lazy { MutableLiveData<LeaveTracker>() }

  suspend fun getAllLeaveTrackers() {
    val leaveTrackers = leaveTrackerRepository.getAllLeaveTracker()
    leaveTrackers.forEach {
      // simulate leave tracker attendance every given time below.
      delay(5000)
      leaveTracker.postValue(it)
    }
  }
}

@Suppress("UNCHECKED_CAST")
class LeaveTrackerViewModelFactory(owner: SavedStateRegistryOwner, args: Bundle?) :
  AbstractSavedStateViewModelFactory(owner, args) {

  private val leaveTrackerRepository: LeaveTrackerRepository by lazy { LeaveTrackerRepository() }

  override fun <T : ViewModel?> create(
    key: String,
    modelClass: Class<T>,
    handle: SavedStateHandle
  ): T = LeaveTrackerViewModel(handle, leaveTrackerRepository) as T
}