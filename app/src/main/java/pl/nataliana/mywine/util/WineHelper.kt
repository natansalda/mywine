package pl.nataliana.mywine.util

import android.content.SharedPreferences
import java.text.DecimalFormat

class WineHelper {

    companion object {
        // TODO put here all methods related to wines operations from MainFragment

        inline fun SharedPreferences.edit(
            commit: Boolean = false,
            action: SharedPreferences.Editor.() -> Unit
        ) {
            val editor = edit()
            action(editor)
            if (commit) {
                editor.commit()
            } else {
                editor.apply()
            }
        }

        fun String.toPriceAmount(): String {
            val dec = DecimalFormat("###,###,###.00")
            return dec.format(this.toDouble())
        }

        fun Double.toPriceAmount(): String {
            val dec = DecimalFormat("###,###,###.00")
            return dec.format(this)
        }
    }

    class PreferencesManager(private val preferences: SharedPreferences?) {

        fun saveWelcomeScreenStatus(token: Boolean) {
            preferences?.edit() {
                putBoolean(WELCOME_SCREEN_PREF, token)
            }
        }

        fun checkWelcomeScreenStatus(): Boolean? {
            return preferences?.getBoolean(WELCOME_SCREEN_PREF, true)
        }

        fun saveOrderMethod(token: String) {
            preferences?.edit() {
                putString(ORDER_METHOD_PREF, token)
            }
        }

        companion object {
            const val WELCOME_SCREEN_PREF = "welcome screen"
            private const val ORDER_METHOD_PREF = "order method"
        }
    }
}