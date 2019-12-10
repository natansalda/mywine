package pl.nataliana.mywine.ui

import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.mywine.R
import pl.nataliana.mywine.model.Wine

@BindingAdapter("textViewName")
fun TextView.setTextViewName(item: Wine?) {
    item?.let {
        text = item.name.capitalize()
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

@BindingAdapter("textViewNameDetail")
fun TextView.setTextViewNameDetail(item: Wine?) {
    item?.let {
        text = item.name.capitalize()
    }
}

@BindingAdapter("textViewYearDetail")
fun TextView.setTextWineYearDetail(item: Wine?) {
    item?.let {
        text = item.year.toString()
    }
}

@BindingAdapter("wineImageDetail")
fun ImageView.setWineImageDetail(item: Wine?) {
    setImageResource(
        when (item?.color) {
            "red" -> R.drawable.red_wine_glass
            "white" -> R.drawable.white_wine_glass
            else -> R.drawable.pink_wine_glass
        }
    )
}

@BindingAdapter("grape1ImageDetail")
fun ImageView.setGrape1ImageDetail(item: Wine?) {
    setImageResource(
        when (item?.rate) {
            5 -> R.drawable.ic_grape_rate_icon_checked
            4 -> R.drawable.ic_grape_rate_icon_checked
            3 -> R.drawable.ic_grape_rate_icon_checked
            2 -> R.drawable.ic_grape_rate_icon_checked
            1 -> R.drawable.ic_grape_rate_icon_checked
            else -> R.drawable.ic_grape_rate_icon_unchecked
        }
    )
}

@BindingAdapter("grape2ImageDetail")
fun ImageView.setGrape2ImageDetail(item: Wine?) {
    setImageResource(
        when (item?.rate) {
            5 -> R.drawable.ic_grape_rate_icon_checked
            4 -> R.drawable.ic_grape_rate_icon_checked
            3 -> R.drawable.ic_grape_rate_icon_checked
            2 -> R.drawable.ic_grape_rate_icon_checked
            1 -> R.drawable.ic_grape_rate_icon_unchecked
            else -> R.drawable.ic_grape_rate_icon_unchecked
        }
    )
}

@BindingAdapter("grape3ImageDetail")
fun ImageView.setGrape3ImageDetail(item: Wine?) {
    setImageResource(
        when (item?.rate) {
            5 -> R.drawable.ic_grape_rate_icon_checked
            4 -> R.drawable.ic_grape_rate_icon_checked
            3 -> R.drawable.ic_grape_rate_icon_checked
            2 -> R.drawable.ic_grape_rate_icon_unchecked
            1 -> R.drawable.ic_grape_rate_icon_unchecked
            else -> R.drawable.ic_grape_rate_icon_unchecked
        }
    )
}

@BindingAdapter("grape4ImageDetail")
fun ImageView.setGrape4ImageDetail(item: Wine?) {
    setImageResource(
        when (item?.rate) {
            5 -> R.drawable.ic_grape_rate_icon_checked
            4 -> R.drawable.ic_grape_rate_icon_checked
            3 -> R.drawable.ic_grape_rate_icon_unchecked
            2 -> R.drawable.ic_grape_rate_icon_unchecked
            1 -> R.drawable.ic_grape_rate_icon_unchecked
            else -> R.drawable.ic_grape_rate_icon_unchecked
        }
    )
}

@BindingAdapter("grape5ImageDetail")
fun ImageView.setGrape5ImageDetail(item: Wine?) {
    setImageResource(
        when (item?.rate) {
            5 -> R.drawable.ic_grape_rate_icon_checked
            4 -> R.drawable.ic_grape_rate_icon_unchecked
            3 -> R.drawable.ic_grape_rate_icon_unchecked
            2 -> R.drawable.ic_grape_rate_icon_unchecked
            1 -> R.drawable.ic_grape_rate_icon_unchecked
            else -> R.drawable.ic_grape_rate_icon_unchecked
        }
    )
}

@BindingAdapter("priceDetail")
fun TextView.setPriceDetail(item: Wine?) {
    item?.let {
        text = item.price.toString()
    }
}

@BindingAdapter("nameEdit")
fun EditText.setNameEdit(item: Wine?) {
    item?.let {
        setText(item.name.capitalize(), TextView.BufferType.EDITABLE)
    }
}

@BindingAdapter("yearEdit")
fun EditText.setYearEdit(item: Wine?) {
    item?.let {
        setText(item.year.toString(), TextView.BufferType.EDITABLE)
    }
}

@BindingAdapter("priceEdit")
fun EditText.setPriceEdit(item: Wine?) {
    item?.let {
        setText(item.price.toString(), TextView.BufferType.EDITABLE)
    }
}

// TODO set color properly
@BindingAdapter("colorRedEdit")
fun RadioButton.setColorRedEdit(item: Wine?) {
    if (item?.color == "red") {
        isChecked
    }
}

@BindingAdapter("colorWhiteEdit")
fun RadioButton.setColorWhiteEdit(item: Wine?) {
    if (item?.color == "white") {
        isChecked
    }
}

@BindingAdapter("colorPinkEdit")
fun RadioButton.setColorPinkEdit(item: Wine?) {
    if (item?.color == "pink") {
        isChecked
    }
}

@BindingAdapter("rateEdit")
fun EditText.setRateEdit(item: Wine?) {
    item?.let {
        setText(item.rate.toString(), TextView.BufferType.EDITABLE)
    }
}