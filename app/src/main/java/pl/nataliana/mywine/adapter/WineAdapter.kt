package pl.nataliana.mywine.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
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
            binding.wine = item
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

// TODO continue by creating data binding
class WineListener(val clickListener: (id: Long) -> Unit) {
    fun onClick(wine: Wine) = clickListener(wine.id)
}