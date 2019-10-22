package pretest.app.attendancetracker.repositories

import pretest.app.attendancetracker.models.LeaveTracker
import pretest.app.attendancetracker.request.endpoints.LeaveTrackers

class LeaveTrackerRepository {
  suspend fun getAllLeaveTracker(): List<LeaveTracker> = LeaveTrackers.get.all().data
}