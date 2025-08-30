package pl.nataliana.mywine.ui

import android.net.Uri
import android.widget.EditText
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.databinding.BindingAdapter
import pl.nataliana.mywine.R
import pl.nataliana.mywine.model.Wine
import pl.nataliana.mywine.util.WineHelper.Companion.toPriceAmount

@BindingAdapter("textViewName")
fun TextView.setTextViewName(item: Wine?) {
    item?.let {
        text = item.name.makeFirstCaseUpperCase()
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
    if (!item.photo.isNullOrEmpty()) {
        try {
            setImageURI(Uri.parse(item.photo))
        } catch (e: Exception) {
            // fallback to color based image
            setImageResource(
                when (item.color) {
                    "red" -> R.drawable.red_wine_glass
                    "white" -> R.drawable.white_wine_glass
                    else -> R.drawable.pink_wine_glass
                }
            )
        }
    } else {
        setImageResource(
            when (item.color) {
                "red" -> R.drawable.red_wine_glass
                "white" -> R.drawable.white_wine_glass
                else -> R.drawable.pink_wine_glass
            }
        )
    }
}

@BindingAdapter("grape1Image")
fun ImageView.setGrape1Image(item: Wine) {
    setImageResource(
        when (item.rate) {
            5F -> R.drawable.ic_grape_rate_icon_checked
            4F -> R.drawable.ic_grape_rate_icon_checked
            3F -> R.drawable.ic_grape_rate_icon_checked
            2F -> R.drawable.ic_grape_rate_icon_checked
            1F -> R.drawable.ic_grape_rate_icon_checked
            else -> R.drawable.ic_grape_rate_icon_unchecked
        }
    )
}

@BindingAdapter("grape2Image")
fun ImageView.setGrape2Image(item: Wine) {
    setImageResource(
        when (item.rate) {
            5F -> R.drawable.ic_grape_rate_icon_checked
            4F -> R.drawable.ic_grape_rate_icon_checked
            3F -> R.drawable.ic_grape_rate_icon_checked
            2F -> R.drawable.ic_grape_rate_icon_checked
            1F -> R.drawable.ic_grape_rate_icon_unchecked
            else -> R.drawable.ic_grape_rate_icon_unchecked
        }
    )
}

@BindingAdapter("grape3Image")
fun ImageView.setGrape3Image(item: Wine) {
    setImageResource(
        when (item.rate) {
            5F -> R.drawable.ic_grape_rate_icon_checked
            4F -> R.drawable.ic_grape_rate_icon_checked
            3F -> R.drawable.ic_grape_rate_icon_checked
            2F -> R.drawable.ic_grape_rate_icon_unchecked
            1F -> R.drawable.ic_grape_rate_icon_unchecked
            else -> R.drawable.ic_grape_rate_icon_unchecked
        }
    )
}

@BindingAdapter("grape4Image")
fun ImageView.setGrape4Image(item: Wine) {
    setImageResource(
        when (item.rate) {
            5F -> R.drawable.ic_grape_rate_icon_checked
            4F -> R.drawable.ic_grape_rate_icon_checked
            3F -> R.drawable.ic_grape_rate_icon_unchecked
            2F -> R.drawable.ic_grape_rate_icon_unchecked
            1F -> R.drawable.ic_grape_rate_icon_unchecked
            else -> R.drawable.ic_grape_rate_icon_unchecked
        }
    )
}

@BindingAdapter("grape5Image")
fun ImageView.setGrape5Image(item: Wine) {
    setImageResource(
        when (item.rate) {
            5F -> R.drawable.ic_grape_rate_icon_checked
            4F -> R.drawable.ic_grape_rate_icon_unchecked
            3F -> R.drawable.ic_grape_rate_icon_unchecked
            2F -> R.drawable.ic_grape_rate_icon_unchecked
            1F -> R.drawable.ic_grape_rate_icon_unchecked
            else -> R.drawable.ic_grape_rate_icon_unchecked
        }
    )
}

@BindingAdapter("textViewNameDetail")
fun TextView.setTextViewNameDetail(item: Wine?) {
    item?.let {
        text = item.name.makeFirstCaseUpperCase()
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
    item?.let {
        if (!it.photo.isNullOrEmpty()) {
            try {
                setImageURI(Uri.parse(it.photo))
            } catch (e: Exception) {
                // fallback to color based image
                setImageResource(
                    when (it.color) {
                        "red" -> R.drawable.red_wine_glass
                        "white" -> R.drawable.white_wine_glass
                        else -> R.drawable.pink_wine_glass
                    }
                )
            }
        } else {
            setImageResource(
                when (it.color) {
                    "red" -> R.drawable.red_wine_glass
                    "white" -> R.drawable.white_wine_glass
                    else -> R.drawable.pink_wine_glass
                }
            )
        }
    }
}

@BindingAdapter("grape1ImageDetail")
fun ImageView.setGrape1ImageDetail(item: Wine?) {
    setImageResource(
        when (item?.rate) {
            5F -> R.drawable.ic_grape_rate_icon_checked
            4F -> R.drawable.ic_grape_rate_icon_checked
            3F -> R.drawable.ic_grape_rate_icon_checked
            2F -> R.drawable.ic_grape_rate_icon_checked
            1F -> R.drawable.ic_grape_rate_icon_checked
            else -> R.drawable.ic_grape_rate_icon_unchecked
        }
    )
}

@BindingAdapter("grape2ImageDetail")
fun ImageView.setGrape2ImageDetail(item: Wine?) {
    setImageResource(
        when (item?.rate) {
            5F -> R.drawable.ic_grape_rate_icon_checked
            4F -> R.drawable.ic_grape_rate_icon_checked
            3F -> R.drawable.ic_grape_rate_icon_checked
            2F -> R.drawable.ic_grape_rate_icon_checked
            1F -> R.drawable.ic_grape_rate_icon_unchecked
            else -> R.drawable.ic_grape_rate_icon_unchecked
        }
    )
}

@BindingAdapter("grape3ImageDetail")
fun ImageView.setGrape3ImageDetail(item: Wine?) {
    setImageResource(
        when (item?.rate) {
            5F -> R.drawable.ic_grape_rate_icon_checked
            4F -> R.drawable.ic_grape_rate_icon_checked
            3F -> R.drawable.ic_grape_rate_icon_checked
            2F -> R.drawable.ic_grape_rate_icon_unchecked
            1F -> R.drawable.ic_grape_rate_icon_unchecked
            else -> R.drawable.ic_grape_rate_icon_unchecked
        }
    )
}

@BindingAdapter("grape4ImageDetail")
fun ImageView.setGrape4ImageDetail(item: Wine?) {
    setImageResource(
        when (item?.rate) {
            5F -> R.drawable.ic_grape_rate_icon_checked
            4F -> R.drawable.ic_grape_rate_icon_checked
            3F -> R.drawable.ic_grape_rate_icon_unchecked
            2F -> R.drawable.ic_grape_rate_icon_unchecked
            1F -> R.drawable.ic_grape_rate_icon_unchecked
            else -> R.drawable.ic_grape_rate_icon_unchecked
        }
    )
}

@BindingAdapter("grape5ImageDetail")
fun ImageView.setGrape5ImageDetail(item: Wine?) {
    setImageResource(
        when (item?.rate) {
            5F -> R.drawable.ic_grape_rate_icon_checked
            4F -> R.drawable.ic_grape_rate_icon_unchecked
            3F -> R.drawable.ic_grape_rate_icon_unchecked
            2F -> R.drawable.ic_grape_rate_icon_unchecked
            1F -> R.drawable.ic_grape_rate_icon_unchecked
            else -> R.drawable.ic_grape_rate_icon_unchecked
        }
    )
}

@BindingAdapter("priceDetail")
fun TextView.setPriceDetail(item: Wine?) {
    item?.let {
        text = item.price.toPriceAmount().toString()
    }
}

@BindingAdapter("nameEdit")
fun EditText.setNameEdit(item: Wine?) {
    item?.let {
        setText(item.name.makeFirstCaseUpperCase(), TextView.BufferType.EDITABLE)
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
        setText(item.price.toPriceAmount().toString(), TextView.BufferType.EDITABLE)
    }
}

// TODO set color of wine properly
@BindingAdapter("colorRedEdit")
fun RadioButton.setColorRedEdit(item: Wine?) {
    if (item?.color == "red") {
        isChecked
    }
}

// TODO set color of wine properly
@BindingAdapter("colorWhiteEdit")
fun RadioButton.setColorWhiteEdit(item: Wine?) {
    if (item?.color == "white") {
        isChecked
    }
}

// TODO set color of wine properly
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

fun String.makeFirstCaseUpperCase() = replaceFirstChar(Char::titlecase)