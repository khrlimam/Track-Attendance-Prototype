package pretest.app.attendancetracker.viewmodels

import android.os.Bundle
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import kotlinx.coroutines.launch
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

  fun getPendingApprovals(username: String) {
    viewModelScope.launch {
      try {
        _pendingRequestState.postValue(RequestState.LoadingStart)
        _pendingApprovals.postValue(repository.getPending(username))
      } catch (e: Exception) {
        _pendingRequestState.postValue(BaseErrorState(e))
      } finally {
        _pendingRequestState.postValue(RequestState.LoadingFinish)
      }
    }
  }

  fun getApprovedApprovals(username: String) {
    viewModelScope.launch {
      try {
        _approvedRequestState.postValue(RequestState.LoadingStart)
        _approvedApprovals.postValue(repository.getApproved(username))
      } catch (e: Exception) {
        _approvedRequestState.postValue(BaseErrorState(e))
      } finally {
        _approvedRequestState.postValue(RequestState.LoadingFinish)
      }
    }
  }

  fun getRejectedApprovals(username: String) {
    viewModelScope.launch {
      try {
        _rejectedRequestState.postValue(RequestState.LoadingStart)
        _rejectedApprovals.postValue(repository.getRejected(username))
      } catch (e: Exception) {
        _rejectedRequestState.postValue(BaseErrorState(e))
      }finally {
        _rejectedRequestState.postValue(RequestState.LoadingFinish)
      }
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