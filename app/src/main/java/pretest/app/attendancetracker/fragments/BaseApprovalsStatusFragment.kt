package pretest.app.attendancetracker.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.recyclerview.*
import kotlinx.android.synthetic.main.loading_data_shimmer.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import pretest.app.attendancetracker.R
import pretest.app.attendancetracker.adapters.RecyclerViewWithMediaCardItem
import pretest.app.attendancetracker.adapters.RecyclerViewWithMediaCardItem.DataHolder
import pretest.app.attendancetracker.utils.gone
import pretest.app.attendancetracker.utils.visible
import pretest.app.attendancetracker.viewmodels.ApprovalsViewModel

abstract class BaseApprovalsStatusFragment : Fragment() {

  var mApprovalsViewModel: ApprovalsViewModel? = null
  private val mData: MutableList<DataHolder> = mutableListOf()
  private val mAdapter = RecyclerViewWithMediaCardItem(mData)

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? = inflater.inflate(R.layout.recyclerview, container, false)

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupRecyclerView(view.context)
    GlobalScope.launch {
      loadingStart()
      requestApproval()
    }
  }

  private fun setupRecyclerView(context:Context) {
    recyclerview.adapter = mAdapter
    recyclerview.layoutManager = LinearLayoutManager(context)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    dataToObserve()?.observe(this, observeApprovalData())
  }

  abstract suspend fun requestApproval()
  abstract fun dataToObserve(): LiveData<List<DataHolder>>?

  private fun observeApprovalData() = Observer<List<DataHolder>> {
    loadingDone()
    if (it.isEmpty()) notFound.visible()
    else {
      recyclerview.visible()
      mData.clear()
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