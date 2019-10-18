package pretest.app.attendancetracker.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import pretest.app.attendancetracker.models.MainActivityNavigationState
import pretest.app.attendancetracker.repositories.DataInits

class MainActivityViewModel() : ViewModel() {

    constructor(savedStateHandle: SavedStateHandle) : this()

    val navigationState: MutableLiveData<MainActivityNavigationState> = DataInits.initMainActivityUiState()

    fun updatePage(newNavigationState: MainActivityNavigationState) {
        navigationState.value = newNavigationState
    }

}