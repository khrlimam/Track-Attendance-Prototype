package pretest.app.attendancetracker.repositories

import pretest.app.attendancetracker.adapters.RecyclerViewWithMediaCardItem.DataHolder
import pretest.app.attendancetracker.models.Approval
import pretest.app.attendancetracker.request.endpoints.Approvals

class ApprovalsRepository {
  suspend fun getPending(username: String): List<DataHolder> =
    Approvals.get.from(username, "pending").data.map(Approval::toDataHolder)

  suspend fun getApproved(username: String): List<DataHolder> =
    Approvals.get.from(username, "approved").data.map(Approval::toDataHolder)

  suspend fun getRejected(username: String): List<DataHolder> =
    Approvals.get.from(username, "rejected").data.map(Approval::toDataHolder)
}