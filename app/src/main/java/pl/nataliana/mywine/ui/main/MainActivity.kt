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
import pl.nataliana.mywine.model.Wine
import pl.nataliana.mywine.model.WinesListViewModel
import pl.nataliana.mywine.ui.detail.AddWineActivity
import pl.nataliana.mywine.ui.detail.AddWineActivity.Companion.EXTRA_COLOR
import pl.nataliana.mywine.ui.detail.AddWineActivity.Companion.EXTRA_NAME
import pl.nataliana.mywine.ui.detail.AddWineActivity.Companion.EXTRA_RATE
import pl.nataliana.mywine.ui.detail.AddWineActivity.Companion.EXTRA_YEAR

class MainActivity : AppCompatActivity() {

    private val wineViewModel: WinesListViewModel by inject()
    private val adapter: WineAdapter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupButtonAddWine()
        setupRecyclerView()

        wineViewModel.getAllWines().observe(this,
            Observer<List<Wine>> { list ->
                list?.let {
                    adapter.setWines(it)
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

    private fun setupRecyclerView() {
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
                val builder = AlertDialog.Builder(this)
                builder.setMessage(getString(R.string.alerd_dialog_delete_wines))
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
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_WINE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val newWine = Wine(
                // TODO type mismatch null and not null
                data.getStringExtra(EXTRA_NAME),
                data.getStringExtra(EXTRA_COLOR),
                data.getIntExtra(EXTRA_YEAR, 0),
                // TODO fix for proper showing of rating
                data.getIntExtra(EXTRA_RATE, 0)
            )
            wineViewModel.insert(newWine)

            Toast.makeText(this, "Wine added!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Wine could not be added!", Toast.LENGTH_SHORT).show()
        }
    }

    companion object{
        private const val ADD_WINE_REQUEST = 1
    }
}
