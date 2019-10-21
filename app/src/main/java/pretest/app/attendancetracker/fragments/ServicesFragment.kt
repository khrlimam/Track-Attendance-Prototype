package pretest.app.attendancetracker.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.loading_data_shimmer.*
import kotlinx.android.synthetic.main.recyclerview.*
import kotlinx.android.synthetic.main.services.recyclerview
import pretest.app.attendancetracker.R
import pretest.app.attendancetracker.adapters.RecyclerViewWithImageBottomLabelItem
import pretest.app.attendancetracker.uis.SpacesItemDecoration
import pretest.app.attendancetracker.utils.gone
import pretest.app.attendancetracker.utils.visible
import pretest.app.attendancetracker.viewmodels.ServiceViewModelFactory
import pretest.app.attendancetracker.viewmodels.ServicesViewModel

class ServicesFragment : Fragment() {

  private val mServicesViewModel: ServicesViewModel by viewModels {
    ServiceViewModelFactory(
      this,
      arguments
    )
  }

  private val mData = mutableListOf<RecyclerViewWithImageBottomLabelItem.DataHolder>()
  private val mAdapter =
    RecyclerViewWithImageBottomLabelItem(mData)

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.recyclerview, container, false)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    loadingStart()
    recyclerview.adapter = mAdapter
    recyclerview.layoutManager = GridLayoutManager(activity?.applicationContext, 2)
    recyclerview.addItemDecoration(SpacesItemDecoration(2, 50, true))
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    mServicesViewModel.servicesMenu.observe(viewLifecycleOwner, Observer {
      loadingDone()
      if (it.isEmpty())
        notFound.visible()
      else {
        recyclerview.visible()
        mData.addAll(it)
        mAdapter.notifyDataSetChanged()
      }
    })
  }

  private fun loadingStart() {
    recyclerview.gone()
    notFound.gone()
    shimmer.visible()
    shimmer.startShimmer()
  }

  private fun loadingDone() {
    shimmer.stopShimmer()
    shimmer.gone()
  }

}