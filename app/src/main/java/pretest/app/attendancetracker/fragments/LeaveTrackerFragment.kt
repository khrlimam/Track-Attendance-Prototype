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
import pretest.app.attendancetracker.R
import pretest.app.attendancetracker.models.LeaveTracker
import pretest.app.attendancetracker.request.RequestState
import pretest.app.attendancetracker.utils.toast
import pretest.app.attendancetracker.viewmodels.LeaveTrackerViewModel
import pretest.app.attendancetracker.viewmodels.LeaveTrackerViewModelFactory


class LeaveTrackerFragment : Fragment() {

  private val mLeaveTrackerViewModel: LeaveTrackerViewModel by viewModels {
    LeaveTrackerViewModelFactory(
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
    mLeaveTrackerViewModel.requestState.observe(this, Observer {
      when (it) {
        is RequestState.Error -> context?.toast(it.reason)
      }
    })
    mLeaveTrackerViewModel.leaveTracker.observe(this, observeLeaveTracker())
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    mLeaveTrackerViewModel.getAllLeaveTrackers()
  }

  private fun observeLeaveTracker(): Observer<in LeaveTracker> = Observer {
    val position = LatLng(it.lat, it._long)
    mMapBox?.addMarker(MarkerOptions().position(position).title(it.name).snippet(it.reason))
    mMapBox?.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 10.0))
  }

  override fun onResume() {
    mapView?.onResume()
    super.onResume()
  }

  override fun onPause() {
    mapView?.onPause()
    super.onPause()
  }

  override fun onStop() {
    mapView?.onStop()
    super.onStop()
  }

  override fun onSaveInstanceState(outState: Bundle) {
    mapView?.onSaveInstanceState(outState)
    super.onSaveInstanceState(outState)
  }

  override fun onLowMemory() {
    mapView?.onLowMemory()
    super.onLowMemory()
  }

  override fun onDestroy() {
    mapView?.onDestroy()
    super.onDestroy()
  }

  override fun onStart() {
    mapView?.onStart()
    super.onStart()
  }

}