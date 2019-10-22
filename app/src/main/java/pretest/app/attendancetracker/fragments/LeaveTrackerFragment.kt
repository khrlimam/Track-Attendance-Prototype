package pretest.app.attendancetracker.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.mapbox.mapboxsdk.Mapbox
import com.mapbox.mapboxsdk.annotations.MarkerOptions
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.maps.Style
import kotlinx.android.synthetic.main.leave_tracker.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pretest.app.attendancetracker.R
import pretest.app.attendancetracker.models.LeaveTracker
import pretest.app.attendancetracker.viewmodels.LeaveTrackerViewModel
import pretest.app.attendancetracker.viewmodels.LeaveTrackkerViewModelFactory


class LeaveTrackerFragment : Fragment() {

  private val mLeaveTrackerViewModel: LeaveTrackerViewModel by viewModels {
    LeaveTrackkerViewModelFactory(
      this,
      arguments
    )
  }
  private var mMapBox: MapboxMap? = null

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.leave_tracker, container, false)

  override fun onAttach(context: Context) {
    Mapbox.getInstance(
      context,
      getString(R.string.mapbox_access_token)
    )
    super.onAttach(context)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    mapView.getMapAsync {
      mMapBox = it
      it.setStyle(Style.MAPBOX_STREETS)
    }
    GlobalScope.launch { mLeaveTrackerViewModel.getAllLeaveTrackers() }
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    mLeaveTrackerViewModel.leaveTracker.observe(this, observeLeaveTracker())
  }

  private fun observeLeaveTracker(): Observer<in LeaveTracker> = Observer {
    val position = LatLng(it.lat, it._long)
    mMapBox?.addMarker(MarkerOptions().position(position).title(it.reason))
    mMapBox?.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 10.0))
  }

  override fun onResume() {
    super.onResume()
    mapView.onResume()
  }

  override fun onPause() {
    super.onPause()
    mapView.onPause()
  }

  override fun onStop() {
    super.onStop()
    mapView.onStop()
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    mapView.onSaveInstanceState(outState)
  }

  override fun onLowMemory() {
    super.onLowMemory()
    mapView.onLowMemory()
  }

  override fun onDestroy() {
    super.onDestroy()
    mapView.onDestroy()
  }

}