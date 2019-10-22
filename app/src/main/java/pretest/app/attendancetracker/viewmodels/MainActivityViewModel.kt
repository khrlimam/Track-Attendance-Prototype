package pretest.app.attendancetracker.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pretest.app.attendancetracker.models.MainActivityNavigationState

class MainActivityViewModel : ViewModel() {

  val navigationState: MutableLiveData<MainActivityNavigationState> by lazy { MutableLiveData<MainActivityNavigationState>() }
  val returnHome: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }

  fun updatePage(newNavigationState: MainActivityNavigationState) {
    navigationState.value = newNavigationState
  }

  fun returnHomeOnBackPressed(bool: Boolean) {
    returnHome.value = bool
  }

}
