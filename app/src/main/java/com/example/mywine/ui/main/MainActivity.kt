package com.example.mywine.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mywine.R
import com.example.mywine.adapter.WineAdapter
import com.example.mywine.model.Wine
import com.example.mywine.ui.detail.AddWineActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

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
                wineViewModel.deleteAllWines()
                Toast.makeText(this, "All wines deleted!", Toast.LENGTH_LONG).show()
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
                data.getStringExtra(AddWineActivity.EXTRA_NAME),
                data.getStringExtra(AddWineActivity.EXTRA_COLOR),
                data.getIntExtra(AddWineActivity.EXTRA_YEAR, 2003),
                data.getDoubleExtra(AddWineActivity.EXTRA_RATE, 4.5)
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
