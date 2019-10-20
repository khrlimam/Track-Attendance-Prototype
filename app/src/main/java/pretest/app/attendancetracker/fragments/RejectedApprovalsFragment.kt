package pretest.app.attendancetracker.fragments

import androidx.lifecycle.LiveData
import pretest.app.attendancetracker.adapters.RecyclerViewWithMediaCardItem.DataHolder
import pretest.app.attendancetracker.utils.Statics.MOCK_USERNAME
import pretest.app.attendancetracker.viewmodels.ApprovalsViewModel

class RejectedApprovalsFragment : BaseApprovalsStatusFragment() {

  companion object {
    fun newInstance(approvalsViewModel: ApprovalsViewModel): RejectedApprovalsFragment {
      val fragment = RejectedApprovalsFragment()
      fragment.mApprovalsViewModel = approvalsViewModel
      return fragment
    }
  }

  override suspend fun requestApproval() {
    mApprovalsViewModel?.getRejectedApprovals(MOCK_USERNAME)
  }

  override fun dataToObserve(): LiveData<List<DataHolder>>? = mApprovalsViewModel?.rejectedApprovals

}