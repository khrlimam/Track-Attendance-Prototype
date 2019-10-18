package pretest.app.attendancetracker.repositories

import androidx.lifecycle.MutableLiveData
import pretest.app.attendancetracker.models.MainActivityNavigationState

object DataInits {
    fun initMainActivityUiState(): MutableLiveData<MainActivityNavigationState> {
        val init = MutableLiveData<MainActivityNavigationState>()
        init.value = MainActivityNavigationState()
        return init
    }
}