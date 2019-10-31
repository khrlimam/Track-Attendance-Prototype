package pretest.app.attendancetracker

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import pretest.app.attendancetracker.utils.MainCoroutineRule

abstract class CoroutineBaseRuleTest {
  // Run tasks synchronously
  @get:Rule
  val instantTaskExecutorRule = InstantTaskExecutorRule()

  // Sets the main coroutines dispatcher to a TestCoroutineScope for unit testing.
  @ExperimentalCoroutinesApi
  @get:Rule
  var mainCoroutineRule = MainCoroutineRule()
}