package com.example.mywine.ui.main

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mywine.R
import com.example.mywine.adapter.WineAdapter
import com.example.mywine.model.Wine
import com.example.mywine.ui.detail.AddWineActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {

    private val noteViewModel: WinesListViewModel by inject()
    private val adapter: WineAdapter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        setupButtonAddWine()
        setupRecyclerView()

        noteViewModel.getAllWines().observe(this,
            Observer<List<Wine>> { list ->
                list?.let {
                    adapter.setWines(it)
                }
            })
    }

    //TODO
//    private fun setupButtonAddWine() {
//        buttonAddNote.setOnClickListener {
//            startActivityForResult(
//                Intent(this, AddNoteActivity::class.java),
//                ADD_NOTE_REQUEST
//            )
//        }
//    }

    private fun setupRecyclerView() {
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.setHasFixedSize(true)
        recycler_view.adapter = adapter
    }

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.main_menu, menu)
//        return true
//    }

//    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
//        return when (item?.itemId) {
//            R.id.delete_all_notes -> {
//                noteViewModel.deleteAllNotes()
//                Toast.makeText(this, "All notes deleted!", Toast.LENGTH_SHORT).show()
//                true
//            }
//            else -> {
//                super.onOptionsItemSelected(item)
//            }
//        }
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == ADD_WINE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            val newNote = Wine(
                data.getStringExtra(AddWineActivity.EXTRA_NAME),
                data.getStringExtra(AddWineActivity.EXTRA_COLOR),
                data.getIntExtra(AddWineActivity.EXTRA_YEAR, 2003),
                data.getDoubleExtra(AddWineActivity.EXTRA_RATE, 4.5)

            )
            noteViewModel.insert(newNote)

            Toast.makeText(this, "Note saved!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Note not saved!", Toast.LENGTH_SHORT).show()
        }
    }

    companion object{
        private const val ADD_WINE_REQUEST = 1
    }
}
