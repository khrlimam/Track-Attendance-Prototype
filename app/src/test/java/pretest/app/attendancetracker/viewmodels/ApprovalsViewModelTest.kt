package pretest.app.attendancetracker.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jraska.livedata.test
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import pretest.app.attendancetracker.adapters.RecyclerViewWithMediaCardItem.DataHolder

class ApprovalsViewModelTest {


  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  private var fakeData: MutableList<DataHolder> = mutableListOf()

  @Mock
  lateinit var fakeApprovalsRepository: FakeApprovalsRepository
  private lateinit var approvalViewModel: ApprovalsViewModel

  @Before
  fun setup() {
    MockitoAnnotations.initMocks(this)
    fakeData.addAll(
      listOf(
        DataHolder("a", "b", "c", "d"),
        DataHolder("a", "b", "c", "d")
      )
    )

    fakeApprovalsRepository.addAllFakeData(fakeData)
    approvalViewModel = ApprovalsViewModel(fakeApprovalsRepository)
  }

  @Test
  fun whenPendingApprovalsRequested_makeSureLiveDataGotTheData() {
    runBlocking {
      val pendingData = fakeApprovalsRepository.getPending("fake")
      `when`(pendingData).thenReturn(fakeData)

      approvalViewModel.pendingApprovals.value = pendingData

      val livePendingData = approvalViewModel.pendingApprovals.test()
        .assertHasValue()
        .assertValue { it == pendingData }
        .value()

      Assert.assertEquals(pendingData, livePendingData)
    }
  }

  @Test
  fun whenApprovedApprovalsRequested_makeSureLiveDataGotTheData() {
    runBlocking {
      val approvedData = fakeApprovalsRepository.getApproved("fake")
      `when`(approvedData).thenReturn(fakeData)

      approvalViewModel.approvedApprovals.value = approvedData

      val liveAppvedData = approvalViewModel.approvedApprovals.test()
        .assertHasValue()
        .assertValue { it == approvedData }
        .value()

      Assert.assertEquals(approvedData, liveAppvedData)
    }
  }

  @Test
  fun whenRejectedApprovalsRequested_makeSureLiveDataGotTheData() {
    runBlocking {
      val rejectedData = fakeApprovalsRepository.getRejected("fake")
      `when`(rejectedData).thenReturn(fakeData)

      approvalViewModel.rejectedApprovals.postValue(rejectedData)

      val liveRejectedData = approvalViewModel.rejectedApprovals.test()
        .assertHasValue()
        .assertValue{ it == rejectedData}
        .value()

      Assert.assertEquals(rejectedData, liveRejectedData)
    }
  }

}