package pretest.app.attendancetracker.uis

import com.google.android.material.tabs.TabLayout

class OnTabSelected(val justDo: (TabLayout.Tab) -> Unit) : TabLayout.OnTabSelectedListener {
  override fun onTabReselected(tab: TabLayout.Tab?) {}

  override fun onTabUnselected(tab: TabLayout.Tab?) {}

  override fun onTabSelected(tab: TabLayout.Tab?) {
    tab?.let { justDo(it) }
  }
}