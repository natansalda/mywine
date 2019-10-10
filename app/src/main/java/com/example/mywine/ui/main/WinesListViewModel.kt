package com.example.mywine.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mywine.model.Wines

class WinesListViewModel : ViewModel() {

    private val wines: MutableLiveData<List<Wines>> by lazy {
        MutableLiveData<List<Wines>>().also {
            loadWines()
        }
    }

    fun getWines(): LiveData<List<Wines>> {
        return wines
    }

    private fun loadWines() {
        // Do an asynchronous operation to fetch wines.
    }
}