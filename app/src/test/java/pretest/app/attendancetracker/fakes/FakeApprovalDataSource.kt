package pretest.app.attendancetracker.fakes

import pretest.app.attendancetracker.datasources.ApprovalDataSource
import pretest.app.attendancetracker.models.Approval

class FakeApprovalDataSource : ApprovalDataSource {

  companion object {
    const val GOOD_ATTENDANCE = "khairulimam"
    const val BAD_ATTENDANCE = "user-test"
  }

  override suspend fun getUserPendingApproval(username: String): List<Approval> =
    fakeData.getOrDefault(username, emptyList())

  override suspend fun getUserApprovedApproval(username: String): List<Approval> =
    fakeData.getOrDefault(username, emptyList())

  override suspend fun getUserRejectedApproval(username: String): List<Approval> =
    fakeData.getOrDefault(username, emptyList())

  val fakeData = mapOf(
    GOOD_ATTENDANCE to listOf(
      Approval("2019-10-10 10:10:10", true, "feeling fresh"),
      Approval("2019-10-11 10:10:10", true, "feeling fresh"),
      Approval("2019-10-12 10:10:10", true, "feeling fresh"),
      Approval("2019-10-13 10:10:10", true, "feeling fresh")
    ),
    BAD_ATTENDANCE to listOf(
      Approval("2019-10-10 10:10:10", false, "lazy"),
      Approval("2019-10-11 10:10:10", false, "just lazy"),
      Approval("2019-10-12 10:10:10", false, "can't move from bed"),
      Approval("2019-10-13 10:10:10", false, "feeling comfy on bed")
    )
  )
}