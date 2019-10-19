package pretest.app.attendancetracker.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pretest.app.attendancetracker.models.MainActivityNavigationState

class MainActivityViewModel : ViewModel() {

  val navigationState: MutableLiveData<MainActivityNavigationState> by lazy {
    MutableLiveData<MainActivityNavigationState>().apply {
      value = MainActivityNavigationState(bottomMenuSelected = "")
    }
  }

  fun updatePage(newNavigationState: MainActivityNavigationState) {
    navigationState.value = newNavigationState
  }

}
