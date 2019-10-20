package pretest.app.attendancetracker.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import kotlinx.android.synthetic.main.approvals.*
import pretest.app.attendancetracker.R
import pretest.app.attendancetracker.adapters.ApprovalsPagerAdapter
import pretest.app.attendancetracker.models.FragmentPage
import pretest.app.attendancetracker.viewmodels.ApprovalsViewModel
import pretest.app.attendancetracker.viewmodels.ApprovalsViewModelFactory

class ApprovalsFragment : Fragment() {


  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.approvals, container, false)

  private val mApprovalsViewModel: ApprovalsViewModel by viewModels {
    ApprovalsViewModelFactory(
      this,
      arguments
    )
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    viewPager.adapter = ApprovalsPagerAdapter(
      childFragmentManager, listOf(
        FragmentPage("Pending", PendingApprovalsFragment.newInstance(mApprovalsViewModel)),
        FragmentPage("Approved", ApprovedApprovalsFragment.newInstance(mApprovalsViewModel)),
        FragmentPage("Rejected", RejectedApprovalsFragment.newInstance(mApprovalsViewModel))
      )
    )
    viewPager.offscreenPageLimit = 3
    approvalTabLayout.setupWithViewPager(viewPager)
  }
}