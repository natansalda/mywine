package pl.nataliana.mywine.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mywine.R
import pl.nataliana.mywine.model.Wine

class WineAdapter : RecyclerView.Adapter<WineAdapter.WineHolder>() {
    private var wines: List<Wine> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WineHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.wine_item, parent, false)
        return WineHolder(itemView)
    }

    override fun onBindViewHolder(holder: WineHolder, position: Int) {
        val currentWine = wines[position]
        holder.textViewName.text = currentWine.name
        if (currentWine.color == "red") {
            holder.wineImage.setImageResource(R.drawable.red_wine_glass)
        } else {
            holder.wineImage.setImageResource(R.drawable.white_wine_glass)
        }
        holder.textViewYear.text = currentWine.year.toString()
        holder.grape4.setImageResource(R.drawable.ic_grape_rate_icon_unchecked)
        holder.grape5.setImageResource(R.drawable.ic_grape_rate_icon_unchecked)
    }

    override fun getItemCount(): Int {
        Log.v("WineAdapter", "there is ${wines.size} wines in db")
        return wines.size
    }

    fun setWines(wines: List<Wine>) {
        this.wines = wines
        notifyDataSetChanged()
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
}