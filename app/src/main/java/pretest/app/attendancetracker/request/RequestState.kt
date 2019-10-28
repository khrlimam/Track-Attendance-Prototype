package pretest.app.attendancetracker.request

sealed class RequestState {
  object LoadingStart : RequestState()
  object LoadingFinish : RequestState()
  open class Error(open val reason: String = "") : RequestState()
}

class BaseErrorState(private val e: Exception) : RequestState.Error() {
  override val reason = "Error occured. Reason: ${e.message}"
}