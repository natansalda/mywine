package pl.nataliana.mywine.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import pl.nataliana.mywine.database.WineDatabaseDao

class WinesListViewModel(
    private var repository: WineDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var allWines: LiveData<List<Wine>> = repository.getAllWines()

    private val _navigateToWineDetail = MutableLiveData<Long>()
    val navigateToWineDetail
        get() = _navigateToWineDetail

    private val _navigateToAddWine = MutableLiveData<Long>()
    val navigateToAddWine
        get() = _navigateToAddWine

    fun insert(wine: Wine) {
        repository.insert(wine)
    }

    fun deleteAllWines() {
        repository.deleteAllWines()
    }

    fun getAllWines(): LiveData<List<Wine>> {
        return allWines
    }

    fun onWineClicked(id: Long) {
        _navigateToWineDetail.value = id
    }

    fun onWineDetailNavigated() {
        _navigateToWineDetail.value = null
    }

    fun onAddWineNavigated() {
        _navigateToAddWine.value = null
    }
}