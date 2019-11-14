package pl.nataliana.mywine.ui.main

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mywine.R
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import pl.nataliana.mywine.adapter.WineAdapter
import pl.nataliana.mywine.adapter.WineListener
import pl.nataliana.mywine.model.Wine
import pl.nataliana.mywine.model.WinesListViewModel
import pl.nataliana.mywine.ui.detail.AddWineActivity
import pl.nataliana.mywine.ui.detail.AddWineActivity.Companion.EXTRA_COLOR
import pl.nataliana.mywine.ui.detail.AddWineActivity.Companion.EXTRA_NAME
import pl.nataliana.mywine.ui.detail.AddWineActivity.Companion.EXTRA_RATE
import pl.nataliana.mywine.ui.detail.AddWineActivity.Companion.EXTRA_YEAR

class MainActivity : AppCompatActivity() {

    private val wineViewModel: WinesListViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val adapter = WineAdapter(WineListener { id ->
            // TODO open detail view onClick
            Toast.makeText(applicationContext, "$id", Toast.LENGTH_LONG).show()
        })

        setContentView(R.layout.activity_main)
        setupButtonAddWine()
        setupRecyclerView(adapter)

        wineViewModel.getAllWines().observe(this,
            Observer<List<Wine>> { list ->
                list?.let {
                    adapter.submitList(it)
                }
            })
    }

    private fun setupButtonAddWine() {
        add_wine_button.setOnClickListener {
            startActivityForResult(
                Intent(this, AddWineActivity::class.java),
                ADD_WINE_REQUEST
            )
        }
    }

    private fun setupRecyclerView(adapter: WineAdapter) {
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(false)
        recycler_view.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_all_wines -> {
                confirmDeletion()
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun confirmDeletion(): Boolean {
        val builder = AlertDialog.Builder(this)
        builder.setMessage(getString(R.string.alert_dialog_delete_wines))
        builder.setPositiveButton(android.R.string.ok) { _, _ ->
            wineViewModel.deleteAllWines()
            Toast.makeText(
                this,
                getString(R.string.wines_deleted_confirmation),
                Toast.LENGTH_LONG
            ).show()
        }

        builder.setNegativeButton(android.R.string.cancel) { _, _ ->
            Toast.makeText(
                applicationContext,
                getString(R.string.wines_deleted_cancelled), Toast.LENGTH_SHORT
            ).show()
        }

        builder.show()
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_WINE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val newWine = Wine(
                // TODO type mismatch null and not null
                data.getStringExtra(EXTRA_NAME),
                data.getStringExtra(EXTRA_COLOR),
                data.getIntExtra(EXTRA_YEAR, 0),
                data.getIntExtra(EXTRA_RATE, 0)
            )
            wineViewModel.insert(newWine)

            Toast.makeText(this, getString(R.string.wine_added_toast), Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, getString(R.string.wine_could_not_be_added), Toast.LENGTH_SHORT).show()
        }
    }

    companion object{
        private const val ADD_WINE_REQUEST = 1
    }
}
