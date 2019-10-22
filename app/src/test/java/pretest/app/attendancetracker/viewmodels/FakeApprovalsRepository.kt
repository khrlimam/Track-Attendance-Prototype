package pretest.app.attendancetracker.viewmodels

import pretest.app.attendancetracker.adapters.RecyclerViewWithMediaCardItem
import pretest.app.attendancetracker.repositories.ApprovalsRepository

class FakeApprovalsRepository : ApprovalsRepository() {

  private val fakeData = mutableListOf<RecyclerViewWithMediaCardItem.DataHolder>()

  fun addAllFakeData(listData: List<RecyclerViewWithMediaCardItem.DataHolder>) {
    fakeData.addAll(listData)
  }

  fun addFakeData(data: RecyclerViewWithMediaCardItem.DataHolder) {
    fakeData.add(data)
  }

  fun clearFakeDAta() {
    fakeData.clear()
  }

  override suspend fun getPending(username: String): List<RecyclerViewWithMediaCardItem.DataHolder> {
    return fakeData
  }

  override suspend fun getApproved(username: String): List<RecyclerViewWithMediaCardItem.DataHolder> {
    return fakeData
  }

  override suspend fun getRejected(username: String): List<RecyclerViewWithMediaCardItem.DataHolder> {
    return fakeData
  }

}