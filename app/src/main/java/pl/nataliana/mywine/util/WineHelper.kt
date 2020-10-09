package pl.nataliana.mywine.util

import java.text.DecimalFormat

class WineHelper {

    companion object {
        // TODO put here all methods related to wines operations from MainFragment

        fun String.toPriceAmount(): String {
            val dec = DecimalFormat("###,###,###.00")
            return dec.format(this.toDouble())
        }

        fun Double.toPriceAmount(): String {
            val dec = DecimalFormat("###,###,###.00")
            return dec.format(this)
        }
    }
}