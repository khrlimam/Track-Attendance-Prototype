package pretest.app.attendancetracker

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import pretest.app.attendancetracker.Statics.EXTRA_BUNDLE
import pretest.app.attendancetracker.models.MainActivityNavigationState
import pretest.app.attendancetracker.models.ProfileInfo
import pretest.app.attendancetracker.viewmodels.MainActivityViewModel
import pretest.app.attendancetracker.viewmodels.MainActivityViewModelFactory

class MainActivity : AppCompatActivity() {

  private val mViewModel: MainActivityViewModel by viewModels {
    MainActivityViewModelFactory(this, intent.getBundleExtra(EXTRA_BUNDLE))
  }

  lateinit var navController: NavController

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    navController = Navigation.findNavController(this, R.id.content)

    bottomTabMenu.addOnTabSelectedListener(onTabSelectedListener())
    mViewModel.navigationState.observe(this, observeNavStateChange())
    mViewModel.profileInfo.observe(this, observeProfileInfoState())
  }

  private fun changePage(title: String, page: Int, menuPosition: Int) {
    tvPageTitle.text = title
    bottomTabMenu.getTabAt(menuPosition)?.select()
    navController.navigate(page, null, Statics.fadeInFadeOutTransition)
  }

  private fun observeProfileInfoState() = Observer<ProfileInfo> {
    it.pictureUrl?.let { it1 -> ivUserPicture.loadFromUrl(it1) }
  }

  private fun observeNavStateChange() = Observer<MainActivityNavigationState> {
    when (it.bottomMenuSelected) {
      getString(R.string.services) -> changePage(
        it.pageTitle,
        R.id.servicesFragment,
        it.menuPosition
      )
      getString(R.string.approvals) -> changePage(
        it.pageTitle,
        R.id.approvalsFragment,
        it.menuPosition
      )
    }
  }

  private fun onTabSelectedListener(): TabLayout.OnTabSelectedListener {
    return object : TabLayout.OnTabSelectedListener {
      override fun onTabReselected(tab: TabLayout.Tab?) {}

      override fun onTabUnselected(tab: TabLayout.Tab?) {}

      override fun onTabSelected(tab: TabLayout.Tab?) {
        tab?.let {
          val menu = it.text.toString()
          mViewModel.updatePage(MainActivityNavigationState(menu, menu, it.position))
        }
      }
    }
  }
}
