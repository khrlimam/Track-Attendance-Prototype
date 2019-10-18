package pretest.app.attendancetracker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.image_bottomlabel_item.view.*

class RecyclerviewWithImageAndLabelBottomItem(val data: List<DataHolder>) :
  RecyclerView.Adapter<RecyclerviewWithImageAndLabelBottomItem.ViewHolder>() {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return ViewHolder(
      LayoutInflater.from(parent.context)
        .inflate(R.layout.image_bottomlabel_item, parent, false)
    )
  }

  override fun getItemCount(): Int = data.size

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(data[position])
  }

  class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(dataHolder: DataHolder) {
      itemView.apply {
        tvLabelBottom.text = dataHolder.labelBottom
        ivIcon.setImageDrawable(resources.getDrawable(dataHolder.drawable, null))
      }
    }
  }

  data class DataHolder(val labelBottom: String, val drawable: Int)
}