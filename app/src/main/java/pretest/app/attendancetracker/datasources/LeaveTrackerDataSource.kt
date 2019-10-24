package pretest.app.attendancetracker.datasources

import pretest.app.attendancetracker.models.LeaveTracker
import pretest.app.attendancetracker.request.endpoints.LeaveTrackers

interface LeaveTrackerDataSource {
  suspend fun getAllLeaveTrackers(): List<LeaveTracker>
}

class LeaveTrackerNetworkDataSource : LeaveTrackerDataSource {
  override suspend fun getAllLeaveTrackers(): List<LeaveTracker> = LeaveTrackers.get.all().data
}