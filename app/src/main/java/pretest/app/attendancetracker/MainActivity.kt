package pretest.app.attendancetracker

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.activity_main.*
import pretest.app.attendancetracker.models.MainActivityNavigationState
import pretest.app.attendancetracker.uis.OnTabSelected
import pretest.app.attendancetracker.utils.*
import pretest.app.attendancetracker.viewmodels.MainActivityViewModel

class MainActivity : GuardActivity() {

  private val mViewModel: MainActivityViewModel by viewModels()
  lateinit var navController: NavController
  private var returnHome: Boolean? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    navController = Navigation.findNavController(this, R.id.content)
    setSupportActionBar(toolbar)
    setUpBottomTabMenu()
    initNavStateChangeObserver()
    getUserInfo().pictureUrl?.let { ivUserPicture.setImageFrom(it) }
  }

  private fun initNavStateChangeObserver() {
    mViewModel.navigationState.observe(this, observeNavStateChange())
    mViewModel.returnHome.observe(this, Observer { returnHome = it })
  }

  private fun setUpBottomTabMenu() {
    bottomTabMenu.addOnTabSelectedListener(OnTabSelected {
      val menu = it.text.toString()
      mViewModel.updatePage(MainActivityNavigationState(menu, menu, it.position))
    })
  }

  private fun changePage(title: String, page: Int, menuPosition: Int) {
    tvPageTitle.text = title.titleCase()
    bottomTabMenu.getTabAt(menuPosition)?.select()
    navController.navigate(page, null, Statics.fadeInFadeOutTransition)
  }

  @SuppressLint("DefaultLocale")
  private fun observeNavStateChange() = Observer<MainActivityNavigationState> {
    etSearch.gone()
    when (it.bottomMenuSelected.toLowerCase()) {
      getString(R.string.services).toLowerCase() -> {
        etSearch.visible()
        changePage(it.pageTitle, R.id.servicesFragment, it.menuPosition)
      }
      getString(R.string.approvals).toLowerCase() -> changePage(
        it.pageTitle,
        R.id.approvalsFragment,
        it.menuPosition
      )
      getString(R.string.about).toLowerCase() -> changePage(
        it.pageTitle,
        R.id.aboutFragment,
        it.menuPosition
      )
      getString(R.string.feeds).toLowerCase() -> changePage(
        it.pageTitle,
        R.id.feedsFragment,
        it.menuPosition
      )
      getString(R.string.leave_tracker).toLowerCase() -> changePage(
        it.pageTitle,
        R.id.leaveTrackerFragment,
        it.menuPosition
      )
    }
  }

  override fun onBackPressed() {
    returnHome?.let {
      if (it) {
        val menu = getString(R.string.services)
        mViewModel.updatePage(MainActivityNavigationState(menu, menu, 0))
        mViewModel.returnHomeOnBackPressed(false)
      } else super.onBackPressed()
    } ?: super.onBackPressed()
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.main_menu, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.menu_logout -> {
        logout()
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }
}
