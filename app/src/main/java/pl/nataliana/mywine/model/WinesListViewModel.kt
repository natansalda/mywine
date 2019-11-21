package pl.nataliana.mywine.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import pl.nataliana.mywine.database.WineDatabaseDao

class WinesListViewModel(
    private var repository: WineDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private val uiScope = CoroutineScope(Dispatchers.Main)
    private val bgDispatcher: CoroutineDispatcher = Dispatchers.IO

    private var allWines: LiveData<List<Wine>> = repository.getAllWines()

    private val _navigateToWineDetail = MutableLiveData<Long>()
    val navigateToWineDetail
        get() = _navigateToWineDetail

    private val _navigateToAddWine = MutableLiveData<Long>()
    val navigateToAddWine
        get() = _navigateToAddWine

    private val _navigateToMainFragment = MutableLiveData<Long>()
    val navigateToMainFragment
        get() = _navigateToMainFragment

    fun insert(wine: Wine) {
        repository.insert(wine)
    }

    fun deleteAllWines() {
        repository.deleteAllWines()
    }

    fun getAllWines(): LiveData<List<Wine>> {
        return allWines
    }

    fun getWineDetail(id: Long): Wine? {
        _navigateToWineDetail.value = id
        return repository.getWineDetails(id)
    }

    fun onWineDetailNavigated() {
        _navigateToWineDetail.value = null
    }

    fun onAddWineNavigated() {
        _navigateToAddWine.value = null
    }

    fun onMainFragmentNavigated() {
        _navigateToMainFragment.value = null
    }
}