package pretest.app.attendancetracker

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.activity_main.*
import pretest.app.attendancetracker.models.MainActivityNavigationState
import pretest.app.attendancetracker.uis.OnTabSelected
import pretest.app.attendancetracker.viewmodels.MainActivityViewModel

class MainActivity : AppCompatActivity() {

  private val mViewModel: MainActivityViewModel by viewModels()

  lateinit var navController: NavController

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    navController = Navigation.findNavController(this, R.id.content)

    setUpBottomTabMenu()
    initModelObserver()
    getUserInfo().pictureUrl?.let { ivUserPicture.loadFromUrl(it) }
  }

  private fun initModelObserver() {
    mViewModel.navigationState.observe(this, observeNavStateChange())
  }

  private fun setUpBottomTabMenu() {
    bottomTabMenu.addOnTabSelectedListener(OnTabSelected {
      val menu = it.text.toString()
      mViewModel.updatePage(MainActivityNavigationState(menu, menu, it.position))
    })
  }

  private fun changePage(title: String, page: Int, menuPosition: Int) {
    tvPageTitle.text = title
    bottomTabMenu.getTabAt(menuPosition)?.select()
    navController.navigate(page, null, Statics.fadeInFadeOutTransition)
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
}
