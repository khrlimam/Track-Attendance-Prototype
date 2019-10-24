package pretest.app.attendancetracker.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pretest.app.attendancetracker.models.MainActivityNavigationState

class MainActivityViewModel : ViewModel() {

  private val _navigationState: MutableLiveData<MainActivityNavigationState> by lazy { MutableLiveData<MainActivityNavigationState>() }
  val navigationState: LiveData<MainActivityNavigationState> = _navigationState

  private val _returnHome: MutableLiveData<Boolean> by lazy { MutableLiveData<Boolean>() }
  val returnHome: LiveData<Boolean> = _returnHome

  fun updatePage(newNavigationState: MainActivityNavigationState) {
    _navigationState.value = newNavigationState
  }

  fun returnHomeOnBackPressed(bool: Boolean) {
    _returnHome.value = bool
  }

}
