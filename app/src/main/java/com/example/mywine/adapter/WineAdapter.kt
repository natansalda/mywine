package com.example.mywine.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mywine.R
import com.example.mywine.model.Wine

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
        holder.textViewYear.text = currentWine.year.toString()
        if (currentWine.color == "red") {
            holder.wineImage.setImageResource(R.drawable.red_wine_glass)
        } else {
            holder.wineImage.setImageResource(R.drawable.white_wine_glass)
        }
        holder.ratingBar.numStars = currentWine.rate.toInt()
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
        var ratingBar: RatingBar = itemView.findViewById(R.id.rating_bar)
    }
}