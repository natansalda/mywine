package com.example.mywine.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mywine.model.Wine

class WinesListViewModel : ViewModel() {

    val selectedWine = MutableLiveData<Wine>()

    private val wines: MutableLiveData<List<Wine>> by lazy {
        MutableLiveData<List<Wine>>().also {
            loadWines()
        }
    }

    fun getWines(): LiveData<List<Wine>> {
        return wines
    }

    private fun loadWines() {
        // Do an asynchronous operation to fetch wines.
    }

    // this fun selects particular wine for detail view
    fun selectWine(wine: Wine) {
        selectedWine.value = wine
    }
}