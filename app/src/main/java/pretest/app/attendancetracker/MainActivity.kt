package pretest.app.attendancetracker

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.auth0.android.provider.WebAuthProvider
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pretest.app.attendancetracker.auth.auth0.Auth0LogoutCallback
import pretest.app.attendancetracker.auth.auth0.Auth0LogoutResult
import pretest.app.attendancetracker.contracts.LogoutResult
import pretest.app.attendancetracker.models.MainActivityNavigationState
import pretest.app.attendancetracker.uis.OnTabSelected
import pretest.app.attendancetracker.utils.*
import pretest.app.attendancetracker.utils.Statics.PROFILE_INFO
import pretest.app.attendancetracker.viewmodels.MainActivityViewModel

class MainActivity : GuardActivity() {

  private val mViewModel: MainActivityViewModel by viewModels()

  lateinit var navController: NavController

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    navController = Navigation.findNavController(this, R.id.content)
    setSupportActionBar(toolbar)
    setUpBottomTabMenu()
    initModelObserver()
    getUserInfo().pictureUrl?.let { ivUserPicture.setImageFrom(it) }
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
    etSearch.gone()
    when (it.bottomMenuSelected) {
      getString(R.string.services) -> {
        etSearch.visible()
        changePage(
          it.pageTitle,
          R.id.servicesFragment,
          it.menuPosition
        )
      }
      getString(R.string.approvals) -> changePage(
        it.pageTitle,
        R.id.approvalsFragment,
        it.menuPosition
      )
    }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.main_menu, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    Log.i(localClassName, "onoptionitem selected")
    return when (item.itemId) {
      R.id.menu_logout -> {
        logout()
        true
      }
      else -> super.onOptionsItemSelected(item)
    }
  }
}
