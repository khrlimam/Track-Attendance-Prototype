package pretest.app.attendancetracker.viewmodels

import android.os.Bundle
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import pretest.app.attendancetracker.Statics.PROFILE_INFO
import pretest.app.attendancetracker.models.MainActivityNavigationState
import pretest.app.attendancetracker.models.ProfileInfo

class MainActivityViewModel() : ViewModel() {

  private var savedStateHandle: SavedStateHandle? = null
  val profileInfo: MutableLiveData<ProfileInfo> by lazy { MutableLiveData<ProfileInfo>() }
  val navigationState: MutableLiveData<MainActivityNavigationState> by lazy {
    MutableLiveData<MainActivityNavigationState>().apply {
      value = MainActivityNavigationState()
    }
  }


  constructor(savedStateHandle: SavedStateHandle) : this() {
    this.savedStateHandle = savedStateHandle
    profileInfo.value = savedStateHandle.get(PROFILE_INFO)
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
  ): T {
    return MainActivityViewModel(handle) as T
  }

}