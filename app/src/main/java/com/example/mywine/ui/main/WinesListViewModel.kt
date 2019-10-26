package com.example.mywine.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mywine.WineRepository
import com.example.mywine.model.Wine

class WinesListViewModel(private var repository: WineRepository) : ViewModel() {

    private var allWines: LiveData<List<Wine>> = repository.getAllWines()

    fun insert(wine: Wine) {
        repository.insert(wine)
    }

    fun deleteAllWines() {
        repository.deleteAllWines()
    }

    fun getAllWines(): LiveData<List<Wine>> {
        return allWines
    }
}