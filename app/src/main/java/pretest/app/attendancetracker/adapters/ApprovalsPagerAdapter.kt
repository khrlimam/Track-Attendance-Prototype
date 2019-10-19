package pretest.app.attendancetracker.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import pretest.app.attendancetracker.models.FragmentPage

class ApprovalsPagerAdapter(
  fragmentManager: FragmentManager,
  private val pages: List<FragmentPage>
) :
  FragmentStatePagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

  override fun getPageTitle(position: Int): CharSequence? = pages[position].title
  override fun getItem(position: Int): Fragment = pages[position].fragment
  override fun getCount(): Int = pages.size

}