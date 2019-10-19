package pretest.app.attendancetracker.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.approvals.*
import pretest.app.attendancetracker.R
import pretest.app.attendancetracker.adapters.ApprovalsPagerAdapter
import pretest.app.attendancetracker.models.FragmentPage

class ApprovalsFragment : Fragment() {
  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(
      R.layout.approvals, container, false
    )
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    viewPager.adapter = ApprovalsPagerAdapter(
      childFragmentManager, listOf(
        FragmentPage("Pending", PendingApprovalsFragment()),
        FragmentPage("Approved", ApprovedApprovalsFragment()),
        FragmentPage("Rejected", RejectedApprovalsFragment())
      )
    )

    approvalTabLayout.setupWithViewPager(viewPager)
  }
}