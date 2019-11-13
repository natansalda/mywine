package pl.nataliana.mywine.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mywine.R
import pl.nataliana.mywine.model.Wine

class WineAdapter : ListAdapter<Wine, WineAdapter.WineHolder>(WineDiffCallback()) {
    private var wines: List<Wine> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WineHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.wine_item, parent, false)
        return WineHolder(itemView)
    }

    override fun onBindViewHolder(holder: WineHolder, position: Int) {
        val currentWine = getItem(position)
        holder.textViewName.text = currentWine.name
        setupColorHolder(currentWine, holder)
        holder.textViewYear.text = currentWine.year.toString()
        setupRateHolder(currentWine, holder)
    }

    private fun setupRateHolder(
        currentWine: Wine,
        holder: WineHolder
    ) {
        when (currentWine.rate) {
            5 -> {
                holder.grape1.setImageResource(R.drawable.ic_grape_rate_icon_checked)
                holder.grape2.setImageResource(R.drawable.ic_grape_rate_icon_checked)
                holder.grape3.setImageResource(R.drawable.ic_grape_rate_icon_checked)
                holder.grape4.setImageResource(R.drawable.ic_grape_rate_icon_checked)
                holder.grape5.setImageResource(R.drawable.ic_grape_rate_icon_checked)
            }
            4 -> {
                holder.grape1.setImageResource(R.drawable.ic_grape_rate_icon_checked)
                holder.grape2.setImageResource(R.drawable.ic_grape_rate_icon_checked)
                holder.grape3.setImageResource(R.drawable.ic_grape_rate_icon_checked)
                holder.grape4.setImageResource(R.drawable.ic_grape_rate_icon_checked)
                holder.grape5.setImageResource(R.drawable.ic_grape_rate_icon_unchecked)
            }
            3 -> {
                holder.grape1.setImageResource(R.drawable.ic_grape_rate_icon_checked)
                holder.grape2.setImageResource(R.drawable.ic_grape_rate_icon_checked)
                holder.grape3.setImageResource(R.drawable.ic_grape_rate_icon_checked)
                holder.grape4.setImageResource(R.drawable.ic_grape_rate_icon_unchecked)
                holder.grape5.setImageResource(R.drawable.ic_grape_rate_icon_unchecked)
            }
            2 -> {
                holder.grape1.setImageResource(R.drawable.ic_grape_rate_icon_checked)
                holder.grape2.setImageResource(R.drawable.ic_grape_rate_icon_checked)
                holder.grape3.setImageResource(R.drawable.ic_grape_rate_icon_unchecked)
                holder.grape4.setImageResource(R.drawable.ic_grape_rate_icon_unchecked)
                holder.grape5.setImageResource(R.drawable.ic_grape_rate_icon_unchecked)
            }
            1 -> {
                holder.grape1.setImageResource(R.drawable.ic_grape_rate_icon_checked)
                holder.grape2.setImageResource(R.drawable.ic_grape_rate_icon_unchecked)
                holder.grape3.setImageResource(R.drawable.ic_grape_rate_icon_unchecked)
                holder.grape4.setImageResource(R.drawable.ic_grape_rate_icon_unchecked)
                holder.grape5.setImageResource(R.drawable.ic_grape_rate_icon_unchecked)
            }
            0 -> {
                holder.grape1.setImageResource(R.drawable.ic_grape_rate_icon_unchecked)
                holder.grape2.setImageResource(R.drawable.ic_grape_rate_icon_unchecked)
                holder.grape3.setImageResource(R.drawable.ic_grape_rate_icon_unchecked)
                holder.grape4.setImageResource(R.drawable.ic_grape_rate_icon_unchecked)
                holder.grape5.setImageResource(R.drawable.ic_grape_rate_icon_unchecked)
            }
        }
    }

    private fun setupColorHolder(
        currentWine: Wine,
        holder: WineHolder
    ) {
        when (currentWine.color) {
            RED -> holder.wineImage.setImageResource(R.drawable.red_wine_glass)
            WHITE -> holder.wineImage.setImageResource(R.drawable.white_wine_glass)
            PINK -> holder.wineImage.setImageResource(R.drawable.pink_wine_glass)
        }
    }

    inner class WineHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewName: TextView = itemView.findViewById(R.id.text_view_name)
        var textViewYear: TextView = itemView.findViewById(R.id.text_view_year)
        var wineImage: ImageView = itemView.findViewById(R.id.wine_image)
        var grape1: ImageView = itemView.findViewById(R.id.grape_1)
        var grape2: ImageView = itemView.findViewById(R.id.grape_2)
        var grape3: ImageView = itemView.findViewById(R.id.grape_3)
        var grape4: ImageView = itemView.findViewById(R.id.grape_4)
        var grape5: ImageView = itemView.findViewById(R.id.grape_5)
    }

    companion object {
        private const val RED = "red"
        private const val PINK = "pink"
        private const val WHITE = "white"

    }
}

class WineDiffCallback :
    DiffUtil.ItemCallback<Wine>() {
    override fun areItemsTheSame(oldItem: Wine, newItem: Wine): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Wine, newItem: Wine): Boolean {
        return oldItem == newItem
    }
}

// TODO continue by creating data binding
class WineListener(val clickListener: (id: Long) -> Unit) {
    fun onClick(wine: Wine) = clickListener(wine.id)

}