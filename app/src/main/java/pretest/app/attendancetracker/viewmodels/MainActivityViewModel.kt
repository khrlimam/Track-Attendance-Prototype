package pretest.app.attendancetracker.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import pretest.app.attendancetracker.models.MainActivityNavigationState

class MainActivityViewModel() : ViewModel() {

  constructor(savedStateHandle: SavedStateHandle) : this()

  val navigationState: MutableLiveData<MainActivityNavigationState> =
    MutableLiveData<MainActivityNavigationState>().apply {
      value = MainActivityNavigationState()
    }

  fun updatePage(newNavigationState: MainActivityNavigationState) {
    navigationState.value = newNavigationState
  }

}