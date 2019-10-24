package pretest.app.attendancetracker.repositories

import pretest.app.attendancetracker.datasources.LeaveTrackerDataSource
import pretest.app.attendancetracker.models.LeaveTracker

class LeaveTrackerRepository(private val leaveTrackerDataSource: LeaveTrackerDataSource) {
  suspend fun getAllLeaveTracker(): List<LeaveTracker> =
    leaveTrackerDataSource.getAllLeaveTrackers()
}