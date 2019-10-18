package pretest.app.attendancetracker

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateViewModelFactory
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_main.*
import pretest.app.attendancetracker.fragments.ApprovalsFragment
import pretest.app.attendancetracker.fragments.ServicesFragment
import pretest.app.attendancetracker.models.MainActivityNavigationState
import pretest.app.attendancetracker.viewmodels.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    private val mViewModel: MainActivityViewModel by viewModels {
        SavedStateViewModelFactory(
            application,
            this
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomTabMenu.addOnTabSelectedListener(onTabSelectedListener())
        mViewModel.navigationState.observe(this, observeUiStateChange())
    }

    private fun changePage(title: String, page: Fragment, menuPosition: Int) {
        tvPageTitle.text = title
        bottomTabMenu.getTabAt(menuPosition)?.select()
        changeContainer(page)
    }

    private fun observeUiStateChange() = Observer<MainActivityNavigationState> {
        when (it.bottomMenuSelected) {
            getString(R.string.services) -> changePage(
                it.pageTitle,
                ServicesFragment(),
                it.menuPosition
            )
            getString(R.string.approvals) -> changePage(
                it.pageTitle,
                ApprovalsFragment(),
                it.menuPosition
            )
        }
    }

    private fun changeContainer(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.content, fragment)
            .commit()
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
