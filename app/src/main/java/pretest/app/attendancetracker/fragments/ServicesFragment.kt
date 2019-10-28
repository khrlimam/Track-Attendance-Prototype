package pretest.app.attendancetracker.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.loading_data_shimmer.*
import kotlinx.android.synthetic.main.recyclerview.*
import pretest.app.attendancetracker.R
import pretest.app.attendancetracker.adapters.RecyclerViewWithImageBottomLabelItem
import pretest.app.attendancetracker.adapters.RecyclerViewWithImageBottomLabelItem.DataHolder
import pretest.app.attendancetracker.models.MainActivityNavigationState
import pretest.app.attendancetracker.request.RequestState
import pretest.app.attendancetracker.uis.SpacesItemDecoration
import pretest.app.attendancetracker.utils.gone
import pretest.app.attendancetracker.utils.toast
import pretest.app.attendancetracker.utils.visible
import pretest.app.attendancetracker.viewmodels.MainActivityViewModel
import pretest.app.attendancetracker.viewmodels.ServiceViewModelFactory
import pretest.app.attendancetracker.viewmodels.ServicesViewModel

class ServicesFragment : Fragment() {

  private val mServicesViewModel: ServicesViewModel by viewModels {
    ServiceViewModelFactory(
      this,
      arguments
    )
  }

  private var mMainActivityViewModel: MainActivityViewModel? = null
  private val mData = mutableListOf<DataHolder>()
  @SuppressLint("DefaultLocale")
  private val mAdapter = RecyclerViewWithImageBottomLabelItem(mData) {
    mMainActivityViewModel?.updatePage(
      MainActivityNavigationState(
        mData[it].labelBottom,
        mData[it].labelBottom,
        0
      )
    )
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.recyclerview, container, false)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    mMainActivityViewModel = activity?.let {
      ViewModelProviders.of(it).get(MainActivityViewModel::class.java)
    }
    recyclerview.adapter = mAdapter
    recyclerview.layoutManager = GridLayoutManager(activity?.applicationContext, 2)
    recyclerview.addItemDecoration(SpacesItemDecoration(2, 50, true))
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    mServicesViewModel.requestState.observe(this, observeRequestState())
    mServicesViewModel.servicesMenu.observe(viewLifecycleOwner, observeServicesData())
  }

  private fun observeRequestState(): Observer<in RequestState> = Observer {
    when (it) {
      is RequestState.LoadingStart -> loadingStart()
      is RequestState.LoadingFinish -> loadingDone()
      is RequestState.Error -> context?.toast(it.reason)
    }
  }

  private fun observeServicesData(): Observer<List<DataHolder>> = Observer {
    if (it.isEmpty())
      notFound.visible()
    else {
      recyclerview.visible()
      mData.addAll(it)
      mAdapter.notifyDataSetChanged()
    }
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