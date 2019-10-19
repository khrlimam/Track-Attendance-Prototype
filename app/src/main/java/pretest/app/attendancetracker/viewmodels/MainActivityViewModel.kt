package pretest.app.attendancetracker.viewmodels

import android.os.Bundle
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import pretest.app.attendancetracker.Statics.PROFILE_INFO
import pretest.app.attendancetracker.models.MainActivityNavigationState
import pretest.app.attendancetracker.models.ProfileInfo

class MainActivityViewModel(private val savedStateHandle: SavedStateHandle) : ViewModel() {

  val profileInfo: LiveData<ProfileInfo> by lazy {
    savedStateHandle.getLiveData<ProfileInfo>(PROFILE_INFO)
  }
  val navigationState: MutableLiveData<MainActivityNavigationState> by lazy {
    MutableLiveData<MainActivityNavigationState>().apply {
      value = MainActivityNavigationState()
    }
  }

  fun updatePage(newNavigationState: MainActivityNavigationState) {
    navigationState.value = newNavigationState
  }

}

@Suppress("UNCHECKED_CAST")
class MainActivityViewModelFactory(
  owner: SavedStateRegistryOwner,
  defaultArgs: Bundle?
) : AbstractSavedStateViewModelFactory(owner, defaultArgs) {
  override fun <T : ViewModel?> create(
    key: String,
    modelClass: Class<T>,
    handle: SavedStateHandle
  ): T = MainActivityViewModel(handle) as T


}