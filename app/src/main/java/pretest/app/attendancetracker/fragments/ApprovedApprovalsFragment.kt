package pretest.app.attendancetracker.fragments

import androidx.lifecycle.MutableLiveData
import pretest.app.attendancetracker.adapters.RecyclerViewWithMediaCardItem.DataHolder
import pretest.app.attendancetracker.utils.Statics.MOCK_USERNAME
import pretest.app.attendancetracker.viewmodels.ApprovalsViewModel

class ApprovedApprovalsFragment : BaseApprovalsStatusFragment() {

  companion object {
    fun newInstance(approvalsViewModel: ApprovalsViewModel): ApprovedApprovalsFragment {
      val fragment = ApprovedApprovalsFragment()
      fragment.mApprovalsViewModel = approvalsViewModel
      return fragment
    }
  }

  override suspend fun requestApproval() {
    mApprovalsViewModel?.getApprovedApprovals(MOCK_USERNAME)
  }

  override fun dataToObserve(): MutableLiveData<List<DataHolder>>? =
    mApprovalsViewModel?.approvedApprovals

}