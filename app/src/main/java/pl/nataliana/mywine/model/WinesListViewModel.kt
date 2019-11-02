package pl.nataliana.mywine.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import pl.nataliana.mywine.WineRepository

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