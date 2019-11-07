package pl.nataliana.mywine.ui.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mywine.R
import kotlinx.android.synthetic.main.activity_add_wine.*

class AddWineActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_wine)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.add_wine_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save_wine -> {
                saveWine()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun saveWine() {
        if (checkIfNameNotEmpty()) return
        if (checkIfColorNotEmpty()) return

        val data = applyWineData()

        setResult(Activity.RESULT_OK, data)
        finish()
    }

    private fun applyWineData(): Intent {
        return Intent().apply {
            val name = edit_text_name.text.toString()
            val color = determineWineColor()
            val year: Int = Integer.valueOf(edit_text_year.text.toString())
            val rating: Int = Integer.valueOf(edit_text_rate.text.toString())

            putExtra(EXTRA_NAME, name)
            putExtra(EXTRA_COLOR, color)
            putExtra(EXTRA_YEAR, year)
            putExtra(EXTRA_RATE, rating)
        }
    }

    private fun determineWineColor(): String? {
        return when {
            // TODO add rose wine
            red_radio_button.isChecked -> getString(R.string.red)
            white_radio_button.isChecked -> getString(R.string.white)
            // will never happen
            else -> null
        }
    }

    private fun checkIfNameNotEmpty(): Boolean {
        if (edit_text_name.text.toString().trim().isBlank()) {
            Toast.makeText(this, getString(R.string.cant_set_empty_record), Toast.LENGTH_LONG)
                .show()
            return true
        }
        return false
    }

    private fun checkIfColorNotEmpty(): Boolean {
        if (determineWineColor() == null) {
            Toast.makeText(this, getString(R.string.cant_set_empty_record), Toast.LENGTH_LONG)
                .show()
            return true
        }
        return false
    }

    companion object {
        const val EXTRA_NAME = "com.example.mywine.EXTRA_NAME"
        const val EXTRA_COLOR = "com.example.mywine.EXTRA_COLOR"
        const val EXTRA_YEAR = "com.example.mywine.EXTRA_YEAR"
        const val EXTRA_RATE = "com.example.mywine.EXTRA_RATE"
    }
}