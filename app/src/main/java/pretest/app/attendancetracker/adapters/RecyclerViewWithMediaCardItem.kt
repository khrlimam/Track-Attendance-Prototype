package pretest.app.attendancetracker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.media_card.view.*
import pretest.app.attendancetracker.R

class RecyclerViewWithMediaCardItem(val data: List<DataHolder>) :
  RecyclerView.Adapter<RecyclerViewWithMediaCardItem.ViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
    LayoutInflater.from(parent.context)
      .inflate(R.layout.media_card, parent, false)
  )

  override fun getItemCount(): Int = data.size

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(data[position])
  }

  class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(dataHolder: DataHolder) {
      itemView.apply {
        tvInitialName.text = dataHolder.nameInitial
        tvTitle.text = dataHolder.title
        tvSubtitle.text = dataHolder.subTitle
        tvDescription.text = dataHolder.description
      }
    }
  }

  data class DataHolder(
    val nameInitial: String,
    val title: String,
    val subTitle: String,
    val description: String
  )
}