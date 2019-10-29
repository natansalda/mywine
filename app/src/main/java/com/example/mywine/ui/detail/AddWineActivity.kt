package com.example.mywine.ui.detail

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
        if (edit_text_name.text.toString().trim().isBlank()) {
            Toast.makeText(this, "Can not insert empty record!", Toast.LENGTH_LONG).show()
            return
        }

        val data = Intent().apply {
            putExtra(EXTRA_NAME, edit_text_name.text.toString())
            putExtra(EXTRA_COLOR, "red")
            putExtra(EXTRA_YEAR, edit_text_year.text.toString())
            putExtra(EXTRA_RATE, 4.5)
        }

        setResult(Activity.RESULT_OK, data)
        finish()
    }

    companion object {
        const val EXTRA_NAME = "com.example.mywine.EXTRA_NAME"
        const val EXTRA_COLOR = "com.example.mywine.EXTRA_COLOR"
        const val EXTRA_YEAR = "com.example.mywine.EXTRA_YEAR"
        const val EXTRA_RATE = "com.example.mywine.EXTRA_RATE"
    }
}