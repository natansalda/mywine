package com.example.mywine.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mywine.R
import com.example.mywine.model.Wine

class WinesListFragment : Fragment() {

//    private lateinit var wineSelector: Selector

    private lateinit var sharedWinesViewModel: WinesListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedWinesViewModel = activity?.run {
            ViewModelProviders.of(this)[WinesListViewModel::class.java]
        } ?: throw Exception("Invalid Activity")
        sharedWinesViewModel.getAllWines().observe(this, Observer<List<Wine>> { wines ->
            // update UI for all wines
        })
        // setOnClickListener z jakiegos powodu nie dziala
//        wineSelector.setOnClickListener { wine: Wine ->
//            // Update the UI for particular wine
//        }
    }

    companion object {
        fun newInstance() = WinesListFragment()
    }
}
