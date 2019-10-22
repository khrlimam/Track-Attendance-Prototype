package pretest.app.attendancetracker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.image_bottomlabel_item.view.*
import pretest.app.attendancetracker.R
import pretest.app.attendancetracker.utils.setImageFrom

class RecyclerViewWithImageBottomLabelItem(
  private val data: List<DataHolder>,
  private val onClick: ((Int) -> Unit)? = null
) :
  RecyclerView.Adapter<RecyclerViewWithImageBottomLabelItem.ViewHolder>() {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    return ViewHolder(
      LayoutInflater.from(parent.context).inflate(R.layout.image_bottomlabel_item, parent, false),
      onClick
    )
  }

  override fun getItemCount(): Int = data.size

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(data[position])
  }

  class ViewHolder(view: View, val onClick: ((Int) -> Unit)? = null) :
    RecyclerView.ViewHolder(view) {
    fun bind(dataHolder: DataHolder) {
      itemView.apply {
        tvLabelBottom.text = dataHolder.labelBottom
        ivIcon.setImageFrom(dataHolder.img)
        setOnClickListener { onClick?.let { it1 -> it1(adapterPosition) } }
      }
    }
  }

  data class DataHolder(val labelBottom: String, val img: String)
}