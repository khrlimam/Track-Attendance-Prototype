package pretest.app.attendancetracker.datasources

import pretest.app.attendancetracker.models.Approval
import pretest.app.attendancetracker.request.endpoints.Approvals

interface ApprovalDataSource {
  suspend fun getUserPendingApproval(username: String): List<Approval>
  suspend fun getUserApprovedApproval(username: String): List<Approval>
  suspend fun getUserRejectedApproval(username: String): List<Approval>
}

class ApprovalNetworkDataSource : ApprovalDataSource {
  override suspend fun getUserPendingApproval(username: String): List<Approval> =
    Approvals.get.from(username, "pending").data


  override suspend fun getUserApprovedApproval(username: String): List<Approval> =
    Approvals.get.from(username, "approved").data

  override suspend fun getUserRejectedApproval(username: String): List<Approval> =
    Approvals.get.from(username, "rejected").data
}