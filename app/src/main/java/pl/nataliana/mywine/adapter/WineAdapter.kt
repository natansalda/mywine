package pl.nataliana.mywine.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import pl.nataliana.mywine.databinding.WineItemBinding
import pl.nataliana.mywine.model.Wine

class WineAdapter(private val clickListener: WineListener) :
    ListAdapter<Wine, WineAdapter.ViewHolder>(WineDiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(clickListener, getItem(position)!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(private val binding: WineItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(clickListener: WineListener, item: Wine) {
            binding.wine = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {

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

class WineListener(val clickListener: (id: Long) -> Unit) {
    fun onClick(wine: Wine) = clickListener(wine.id)
}