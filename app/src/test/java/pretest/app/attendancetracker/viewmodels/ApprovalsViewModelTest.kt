package pretest.app.attendancetracker.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.android.example.livedatabuilder.util.MainCoroutineRule
import com.example.android.architecture.blueprints.todoapp.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pretest.app.attendancetracker.models.Approval
import pretest.app.attendancetracker.repositories.ApprovalsRepository
import pretest.app.attendancetracker.request.RequestState
import pretest.app.attendancetracker.utils.observeOnce

class ApprovalsViewModelTest {

  // Run tasks synchronously
  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  // Sets the main coroutines dispatcher to a TestCoroutineScope for unit testing.
  @ExperimentalCoroutinesApi
  @get:Rule
  var mainCoroutineRule = MainCoroutineRule()

  lateinit var fakeApprovalDataSource: FakeApprovalDataSource
  private lateinit var approvalRepository: ApprovalsRepository
  private lateinit var approvalViewModel: ApprovalsViewModel

  @Before
  fun setup() {
    fakeApprovalDataSource = FakeApprovalDataSource()
    approvalRepository = ApprovalsRepository(fakeApprovalDataSource)
    approvalViewModel = ApprovalsViewModel(approvalRepository)
  }

  @Test
  fun `fire LoadingStart event when requesting pending data`() {
    approvalViewModel.pendingRequestState.observeOnce {
      Assert.assertEquals(RequestState.LoadingStart, it)
    }
    approvalViewModel.getPendingApprovals(FakeApprovalDataSource.GOOD_ATTENDANCE)
  }

  @Test
  fun `fire LoadingStart event when requesting rejected data`() {
    approvalViewModel.rejectedRequestState.observeOnce {
      Assert.assertEquals(RequestState.LoadingStart, it)
    }
    approvalViewModel.getRejectedApprovals(FakeApprovalDataSource.GOOD_ATTENDANCE)
  }

  @Test
  fun `fire LoadingStart event when requesting approved data`() {
    approvalViewModel.approvedRequestState.observeOnce {
      Assert.assertEquals(RequestState.LoadingStart, it)
    }
    approvalViewModel.getApprovedApprovals(FakeApprovalDataSource.GOOD_ATTENDANCE)
  }

  @ExperimentalCoroutinesApi
  @Test
  fun `get pending approval data of given user and fire LoadingFinish event`() {
    val user = FakeApprovalDataSource.GOOD_ATTENDANCE
    approvalViewModel.getPendingApprovals(user)

    val pendingApprovalData = approvalViewModel.pendingApprovals.getOrAwaitValue {
      mainCoroutineRule.advanceUntilIdle()
    }

    Assert.assertEquals(
      pendingApprovalData,
      fakeApprovalDataSource.fakeData[user]?.map(Approval::toDataHolder)
    )

    val pendingRequestState = approvalViewModel.pendingRequestState.getOrAwaitValue()
    Assert.assertEquals(RequestState.LoadingFinish, pendingRequestState)
  }

  @ExperimentalCoroutinesApi
  @Test
  fun `get rejected approval data of given user and fire LoadingFinish event`() {
    val user = FakeApprovalDataSource.BAD_ATTENDANCE
    approvalViewModel.getRejectedApprovals(user)

    val rejectedApprovalData = approvalViewModel.rejectedApprovals.getOrAwaitValue {
      mainCoroutineRule.advanceUntilIdle()
    }

    Assert.assertEquals(
      rejectedApprovalData,
      fakeApprovalDataSource.fakeData[user]?.map(Approval::toDataHolder)
    )

    val rejectedRequestState = approvalViewModel.rejectedRequestState.getOrAwaitValue()
    Assert.assertEquals(RequestState.LoadingFinish, rejectedRequestState)
  }

  @ExperimentalCoroutinesApi
  @Test
  fun `get approved approval data of given user and fire LoadingFinish event`() {
    val user = FakeApprovalDataSource.GOOD_ATTENDANCE
    approvalViewModel.getApprovedApprovals(user)

    val approvedApprovalData = approvalViewModel.approvedApprovals.getOrAwaitValue {
      mainCoroutineRule.advanceUntilIdle()
    }

    Assert.assertEquals(
      approvedApprovalData,
      fakeApprovalDataSource.fakeData[user]?.map(Approval::toDataHolder)
    )

    val approvedRequestState = approvalViewModel.approvedRequestState.getOrAwaitValue()
    Assert.assertEquals(RequestState.LoadingFinish, approvedRequestState)
  }

}
