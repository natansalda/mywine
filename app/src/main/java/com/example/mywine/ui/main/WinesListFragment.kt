package com.example.mywine.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.mywine.R
import com.example.mywine.model.Wines

class WinesListFragment : Fragment() {

    companion object {
        fun newInstance() = WinesListFragment()
    }

    private lateinit var viewModel: WinesListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(WinesListViewModel::class.java)
        viewModel.getWines().observe(this, Observer<List<Wines>> { wines ->
            // update UI
        })
    }
}
