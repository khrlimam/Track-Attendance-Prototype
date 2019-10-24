package pretest.app.attendancetracker.request

sealed class RequestState {
  data class StateLoading(val isLoading: Boolean) : RequestState()
  open class StateError(open val reason: String = "") : RequestState()
}

class BaseErrorState(private val e: Exception) : RequestState.StateError() {
  override val reason = "Error occured. Reason: ${e.message}"
}