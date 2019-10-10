package com.example.mywine.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mywine.model.Wine
import com.example.mywine.ui.main.WinesListViewModel

class WineDetailFragment : Fragment() {

    private lateinit var sharedWinesViewModel: WinesListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedWinesViewModel = activity?.run {
            ViewModelProviders.of(this)[WinesListViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
        sharedWinesViewModel.selectedWine.observe(this, Observer<Wine> { wine ->
            updateChosenWine(wine)
        })
    }

    private fun updateChosenWine(wine: Wine?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}

