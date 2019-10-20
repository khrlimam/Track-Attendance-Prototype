package pretest.app.attendancetracker.viewmodels

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import pretest.app.attendancetracker.adapters.RecyclerViewWithMediaCardItem.DataHolder
import pretest.app.attendancetracker.repositories.ApprovalsRepository

class ApprovalsViewModel(
  savedStateHandle: SavedStateHandle,
  private val repository: ApprovalsRepository
) : ViewModel() {

  val pendingApprovals: MutableLiveData<List<DataHolder>> by lazy { MutableLiveData<List<DataHolder>>() }
  val approvedApprovals: MutableLiveData<List<DataHolder>> by lazy { MutableLiveData<List<DataHolder>>() }
  val rejectedApprovals: MutableLiveData<List<DataHolder>> by lazy { MutableLiveData<List<DataHolder>>() }

  suspend fun getPendingApprovals(username: String) {
    pendingApprovals.postValue(repository.getPending(username))
  }

  suspend fun getApprovedApprovals(username: String) {
    approvedApprovals.postValue(repository.getApproved(username))
  }

  suspend fun getRejectedApprovals(username: String) {
    rejectedApprovals.postValue(repository.getRejected(username))
  }

}

@Suppress("UNCHECKED_CAST")
class ApprovalsViewModelFactory(owner: SavedStateRegistryOwner, args: Bundle?) :
  AbstractSavedStateViewModelFactory(owner, args) {
  override fun <T : ViewModel?> create(
    key: String,
    modelClass: Class<T>,
    handle: SavedStateHandle
  ): T = ApprovalsViewModel(handle, ApprovalsRepository()) as T
}