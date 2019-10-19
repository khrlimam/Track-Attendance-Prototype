package pretest.app.attendancetracker.repositories

import android.content.Context
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import pretest.app.attendancetracker.R
import pretest.app.attendancetracker.adapters.RecyclerViewWithImageAndLabelBottomItem.DataHolder

class ServiceRepository(private val context: Context?) {

  fun getServices(): Deferred<List<DataHolder>> = GlobalScope.async {
    val drawables = context?.resources?.obtainTypedArray(R.array.service_icons)
    val data = context?.resources?.getStringArray(R.array.service_menus)
      ?.withIndex()?.map { indexedLabel ->
        val imageId = drawables?.getResourceId(indexedLabel.index, 0) ?: 0
        DataHolder(indexedLabel.value, imageId)
      }
    drawables?.recycle()
    data ?: listOf()
  }

}