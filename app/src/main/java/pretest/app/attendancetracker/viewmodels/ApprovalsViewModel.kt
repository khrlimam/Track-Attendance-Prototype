package pretest.app.attendancetracker.viewmodels

import android.os.Bundle
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import pretest.app.attendancetracker.adapters.RecyclerViewWithMediaCardItem.DataHolder
import pretest.app.attendancetracker.datasources.ApprovalNetworkDataSource
import pretest.app.attendancetracker.repositories.ApprovalsRepository
import pretest.app.attendancetracker.request.BaseErrorState
import pretest.app.attendancetracker.request.RequestState

class ApprovalsViewModel(
  private val repository: ApprovalsRepository
) : ViewModel() {

  constructor(
    savedStateHandle: SavedStateHandle,
    repository: ApprovalsRepository
  ) : this(repository)

  private val _pendingRequestState: MutableLiveData<RequestState> by lazy { MutableLiveData<RequestState>() }
  val pendingRequestState: LiveData<RequestState> = _pendingRequestState

  private val _approvedRequestState: MutableLiveData<RequestState> by lazy { MutableLiveData<RequestState>() }
  val approvedRequestState: LiveData<RequestState> = _approvedRequestState

  private val _rejectedRequestState: MutableLiveData<RequestState> by lazy { MutableLiveData<RequestState>() }
  val rejectedRequestState: LiveData<RequestState> = _rejectedRequestState

  private val _pendingApprovals: MutableLiveData<List<DataHolder>> by lazy { MutableLiveData<List<DataHolder>>() }
  val pendingApprovals: LiveData<List<DataHolder>> = _pendingApprovals

  private val _approvedApprovals: MutableLiveData<List<DataHolder>> by lazy { MutableLiveData<List<DataHolder>>() }
  val approvedApprovals: LiveData<List<DataHolder>> = _approvedApprovals

  private val _rejectedApprovals: MutableLiveData<List<DataHolder>> by lazy { MutableLiveData<List<DataHolder>>() }
  val rejectedApprovals: LiveData<List<DataHolder>> = _rejectedApprovals

  suspend fun getPendingApprovals(username: String) {
    try {
      _pendingRequestState.postValue(RequestState.StateLoading(true))
      _pendingApprovals.postValue(repository.getPending(username))
      _pendingRequestState.postValue(RequestState.StateLoading(false))
    } catch (e: Exception) {
      _pendingRequestState.postValue(BaseErrorState(e))
    }
  }

  suspend fun getApprovedApprovals(username: String) {
    try {
      _approvedRequestState.postValue(RequestState.StateLoading(true))
      _approvedApprovals.postValue(repository.getApproved(username))
      _approvedRequestState.postValue(RequestState.StateLoading(false))
    } catch (e: Exception) {
      _approvedRequestState.postValue(BaseErrorState(e))
    }
  }

  suspend fun getRejectedApprovals(username: String) {
    try {
      _rejectedRequestState.postValue(RequestState.StateLoading(true))
      _rejectedApprovals.postValue(repository.getRejected(username))
      _rejectedRequestState.postValue(RequestState.StateLoading(false))
    } catch (e: Exception) {
      _rejectedRequestState.postValue(BaseErrorState(e))
    }
  }

}

@Suppress("UNCHECKED_CAST")
class ApprovalsViewModelFactory(owner: SavedStateRegistryOwner, args: Bundle?) :
  AbstractSavedStateViewModelFactory(owner, args) {

  private val approvalNetworkDataSource: ApprovalNetworkDataSource by lazy { ApprovalNetworkDataSource() }

  override fun <T : ViewModel?> create(
    key: String,
    modelClass: Class<T>,
    handle: SavedStateHandle
  ): T = ApprovalsViewModel(handle, ApprovalsRepository(approvalNetworkDataSource)) as T
}