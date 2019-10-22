package pretest.app.attendancetracker.repositories

import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import pretest.app.attendancetracker.adapters.RecyclerViewWithMediaCardItem
import pretest.app.attendancetracker.viewmodels.FakeApprovalsRepository

class ApprovalsRepositoryTest {

  @Mock
  private lateinit var repository: FakeApprovalsRepository

  private val fakeData = listOf<RecyclerViewWithMediaCardItem.DataHolder>(
    RecyclerViewWithMediaCardItem.DataHolder("a", "b", "c", "d"),
    RecyclerViewWithMediaCardItem.DataHolder("a", "b", "c", "d"),
    RecyclerViewWithMediaCardItem.DataHolder("a", "b", "c", "d")
  )

  @Before
  fun setUp() {
    MockitoAnnotations.initMocks(this)
    repository.addAllFakeData(fakeData)
  }

  @Test
  fun getPending() {
    runBlocking { val data = repository.getPending("fake")
      println(data)
    }

  }

  @Test
  fun getApproved() {
  }

  @Test
  fun getRejected() {
  }
}