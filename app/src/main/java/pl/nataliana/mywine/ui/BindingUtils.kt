package pl.nataliana.mywine.ui

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.mywine.R
import pl.nataliana.mywine.model.Wine

@BindingAdapter("textViewName")
fun TextView.setTextViewName(item: Wine?) {
    item?.let {
        text = item.name
    }
}

@BindingAdapter("textViewYear")
fun TextView.setTextWineYear(item: Wine?) {
    item?.let {
        text = item.year.toString()
    }
}

@BindingAdapter("wineImage")
fun ImageView.setWineImage(item: Wine) {
    setImageResource(
        when (item.color) {
            "red" -> R.drawable.red_wine_glass
            "white" -> R.drawable.white_wine_glass
            else -> R.drawable.pink_wine_glass
        }
    )
}

@BindingAdapter("grape1Image")
fun ImageView.setGrape1Image(item: Wine) {
    setImageResource(
        when (item.rate) {
            5 -> R.drawable.ic_grape_rate_icon_checked
            4 -> R.drawable.ic_grape_rate_icon_checked
            3 -> R.drawable.ic_grape_rate_icon_checked
            2 -> R.drawable.ic_grape_rate_icon_checked
            1 -> R.drawable.ic_grape_rate_icon_checked
            else -> R.drawable.ic_grape_rate_icon_unchecked
        }
    )
}

@BindingAdapter("grape2Image")
fun ImageView.setGrape2Image(item: Wine) {
    setImageResource(
        when (item.rate) {
            5 -> R.drawable.ic_grape_rate_icon_checked
            4 -> R.drawable.ic_grape_rate_icon_checked
            3 -> R.drawable.ic_grape_rate_icon_checked
            2 -> R.drawable.ic_grape_rate_icon_checked
            1 -> R.drawable.ic_grape_rate_icon_unchecked
            else -> R.drawable.ic_grape_rate_icon_unchecked
        }
    )
}

@BindingAdapter("grape3Image")
fun ImageView.setGrape3Image(item: Wine) {
    setImageResource(
        when (item.rate) {
            5 -> R.drawable.ic_grape_rate_icon_checked
            4 -> R.drawable.ic_grape_rate_icon_checked
            3 -> R.drawable.ic_grape_rate_icon_checked
            2 -> R.drawable.ic_grape_rate_icon_unchecked
            1 -> R.drawable.ic_grape_rate_icon_unchecked
            else -> R.drawable.ic_grape_rate_icon_unchecked
        }
    )
}

@BindingAdapter("grape4Image")
fun ImageView.setGrape4Image(item: Wine) {
    setImageResource(
        when (item.rate) {
            5 -> R.drawable.ic_grape_rate_icon_checked
            4 -> R.drawable.ic_grape_rate_icon_checked
            3 -> R.drawable.ic_grape_rate_icon_unchecked
            2 -> R.drawable.ic_grape_rate_icon_unchecked
            1 -> R.drawable.ic_grape_rate_icon_unchecked
            else -> R.drawable.ic_grape_rate_icon_unchecked
        }
    )
}

@BindingAdapter("grape5Image")
fun ImageView.setGrape5Image(item: Wine) {
    setImageResource(
        when (item.rate) {
            5 -> R.drawable.ic_grape_rate_icon_checked
            4 -> R.drawable.ic_grape_rate_icon_unchecked
            3 -> R.drawable.ic_grape_rate_icon_unchecked
            2 -> R.drawable.ic_grape_rate_icon_unchecked
            1 -> R.drawable.ic_grape_rate_icon_unchecked
            else -> R.drawable.ic_grape_rate_icon_unchecked
        }
    )
}
