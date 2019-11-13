package pl.nataliana.mywine.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mywine.R
import com.example.mywine.databinding.WineItemBinding
import pl.nataliana.mywine.model.Wine

class WineAdapter : ListAdapter<Wine, WineAdapter.ViewHolder>(WineDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: WineItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Wine) {
            binding.textViewName.text = item.name
            setupColorHolder(item)
            binding.textViewYear.text = item.year.toString()
            setupRateHolder(item)
        }

        private fun setupRateHolder(item: Wine) {
            when (item.rate) {
                5 -> {
                    binding.grape1.setImageResource(R.drawable.ic_grape_rate_icon_checked)
                    binding.grape2.setImageResource(R.drawable.ic_grape_rate_icon_checked)
                    binding.grape3.setImageResource(R.drawable.ic_grape_rate_icon_checked)
                    binding.grape4.setImageResource(R.drawable.ic_grape_rate_icon_checked)
                    binding.grape5.setImageResource(R.drawable.ic_grape_rate_icon_checked)
                }
                4 -> {
                    binding.grape1.setImageResource(R.drawable.ic_grape_rate_icon_checked)
                    binding.grape2.setImageResource(R.drawable.ic_grape_rate_icon_checked)
                    binding.grape3.setImageResource(R.drawable.ic_grape_rate_icon_checked)
                    binding.grape4.setImageResource(R.drawable.ic_grape_rate_icon_checked)
                    binding.grape5.setImageResource(R.drawable.ic_grape_rate_icon_unchecked)
                }
                3 -> {
                    binding.grape1.setImageResource(R.drawable.ic_grape_rate_icon_checked)
                    binding.grape2.setImageResource(R.drawable.ic_grape_rate_icon_checked)
                    binding.grape3.setImageResource(R.drawable.ic_grape_rate_icon_checked)
                    binding.grape4.setImageResource(R.drawable.ic_grape_rate_icon_unchecked)
                    binding.grape5.setImageResource(R.drawable.ic_grape_rate_icon_unchecked)
                }
                2 -> {
                    binding.grape1.setImageResource(R.drawable.ic_grape_rate_icon_checked)
                    binding.grape2.setImageResource(R.drawable.ic_grape_rate_icon_checked)
                    binding.grape3.setImageResource(R.drawable.ic_grape_rate_icon_unchecked)
                    binding.grape4.setImageResource(R.drawable.ic_grape_rate_icon_unchecked)
                    binding.grape5.setImageResource(R.drawable.ic_grape_rate_icon_unchecked)
                }
                1 -> {
                    binding.grape1.setImageResource(R.drawable.ic_grape_rate_icon_checked)
                    binding.grape2.setImageResource(R.drawable.ic_grape_rate_icon_unchecked)
                    binding.grape3.setImageResource(R.drawable.ic_grape_rate_icon_unchecked)
                    binding.grape4.setImageResource(R.drawable.ic_grape_rate_icon_unchecked)
                    binding.grape5.setImageResource(R.drawable.ic_grape_rate_icon_unchecked)
                }
                0 -> {
                    binding.grape1.setImageResource(R.drawable.ic_grape_rate_icon_unchecked)
                    binding.grape2.setImageResource(R.drawable.ic_grape_rate_icon_unchecked)
                    binding.grape3.setImageResource(R.drawable.ic_grape_rate_icon_unchecked)
                    binding.grape4.setImageResource(R.drawable.ic_grape_rate_icon_unchecked)
                    binding.grape5.setImageResource(R.drawable.ic_grape_rate_icon_unchecked)
                }
            }
        }

        private fun setupColorHolder(item: Wine) {
            when (item.color) {
                RED -> binding.wineImage.setImageResource(R.drawable.red_wine_glass)
                WHITE -> binding.wineImage.setImageResource(R.drawable.white_wine_glass)
                PINK -> binding.wineImage.setImageResource(R.drawable.pink_wine_glass)
            }
        }


        companion object {
            private const val RED = "red"
            private const val PINK = "pink"
            private const val WHITE = "white"

            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = WineItemBinding.inflate(layoutInflater, parent, false)

                return ViewHolder(binding)
            }
        }
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