package pretest.app.attendancetracker.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.services.*
import pretest.app.attendancetracker.R
import pretest.app.attendancetracker.RecyclerviewWithImageAndLabelBottomItem
import pretest.app.attendancetracker.uis.SpacesItemDecoration

class ServicesFragment : Fragment() {

    private val mData = mutableListOf<RecyclerviewWithImageAndLabelBottomItem.DataHolder>()
    private val mADapter = RecyclerviewWithImageAndLabelBottomItem(mData)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.services, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val data = prepareMenu()
        recyclerview.adapter = mADapter
        recyclerview.layoutManager = GridLayoutManager(activity?.applicationContext, 2)
        recyclerview.addItemDecoration(SpacesItemDecoration(2, 50, true))
        mData.addAll(data)
    }

    @SuppressLint("Recycle")
    fun prepareMenu(): List<RecyclerviewWithImageAndLabelBottomItem.DataHolder> {
        val drawables = context?.resources?.obtainTypedArray(R.array.service_icons)
        val data = context?.resources?.getStringArray(R.array.service_menus)
            ?.withIndex()?.map { indexedLabel ->
                val imageId = drawables?.getResourceId(indexedLabel.index, 0) ?: 0
                RecyclerviewWithImageAndLabelBottomItem.DataHolder(indexedLabel.value, imageId)
            }
        drawables?.recycle()
        return data ?: listOf()
    }

}