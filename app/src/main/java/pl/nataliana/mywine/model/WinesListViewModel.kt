package pl.nataliana.mywine.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Job
import pl.nataliana.mywine.database.WineDatabaseDao

class WinesListViewModel(
    val database: WineDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()

    private var allWines = database.getAllWines()

    private var allWinesByRatingBest = database.getAllWinesByRatingBest()

    private var allWinesByRatingWorse = database.getAllWinesByRatingWorse()

    private val _navigateToWineDetail = MutableLiveData<Wine>()
    val navigateToWineDetail: LiveData<Wine>
        get() = _navigateToWineDetail

    private val _navigateToAddWine = MutableLiveData<Long>()
    val navigateToAddWine
        get() = _navigateToAddWine

    private val _navigateToMainFragment = MutableLiveData<Long>()
    val navigateToMainFragment
        get() = _navigateToMainFragment

    private val _navigateToWineEdit = MutableLiveData<Wine>()
    val navigateToWineEdit: LiveData<Wine>
        get() = _navigateToWineEdit

    fun insert(wine: Wine) = database.insert(wine)

    fun deleteAllWines() = database.deleteAllWines()

    fun getAllWines(): LiveData<List<Wine>> {
        return allWines
    }

    fun getAllWinesByRatingBest(): LiveData<List<Wine>> {
        return allWinesByRatingBest
    }

    fun getAllWinesByRatingWorse(): LiveData<List<Wine>> {
        return allWinesByRatingWorse
    }

    fun onWineDetailNavigated() {
        _navigateToWineDetail.value = null
    }

    fun onWineEditNavigated() {
        _navigateToWineEdit.value = null
    }

    fun edit(wine: Wine) = database.update(wine)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}