package pretest.app.attendancetracker.fragments

import androidx.lifecycle.LiveData
import pretest.app.attendancetracker.adapters.RecyclerViewWithMediaCardItem.DataHolder
import pretest.app.attendancetracker.request.RequestState
import pretest.app.attendancetracker.utils.Statics
import pretest.app.attendancetracker.viewmodels.ApprovalsViewModel

class PendingApprovalsFragment : BaseApprovalsStatusFragment() {

  companion object {
    fun newInstance(approvalsViewModel: ApprovalsViewModel): PendingApprovalsFragment {
      val fragment = PendingApprovalsFragment()
      fragment.mApprovalsViewModel = approvalsViewModel
      return fragment
    }
  }

  override suspend fun requestApproval() {
    mApprovalsViewModel?.getPendingApprovals(Statics.MOCK_USERNAME)
  }

  override fun dataToObserve(): LiveData<List<DataHolder>>? = mApprovalsViewModel?.pendingApprovals
  override fun requestState(): LiveData<RequestState>? = mApprovalsViewModel?.pendingRequestState

}