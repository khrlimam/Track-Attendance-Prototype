package pretest.app.attendancetracker.repositories

import pretest.app.attendancetracker.adapters.RecyclerViewWithMediaCardItem.DataHolder
import pretest.app.attendancetracker.datasources.ApprovalDataSource
import pretest.app.attendancetracker.models.Approval

class ApprovalsRepository(private val dataSource: ApprovalDataSource) {
  suspend fun getPending(username: String): List<DataHolder> =
    dataSource.getUserPendingApproval(username).map(Approval::toDataHolder)

  suspend fun getApproved(username: String): List<DataHolder> =
    dataSource.getUserApprovedApproval(username).map(Approval::toDataHolder)

  suspend fun getRejected(username: String): List<DataHolder> =
    dataSource.getUserRejectedApproval(username).map(Approval::toDataHolder)
}