package pl.nataliana.mywine.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import pl.nataliana.mywine.database.WineDatabaseDao

class WinesListViewModel(
    val database: WineDatabaseDao,
    application: Application
) : AndroidViewModel(application) {

    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    private var particularWine = MutableLiveData<Wine?>()
    private var allWines = database.getAllWines()

    private val _navigateToWineDetail = MutableLiveData<Wine>()
    val navigateToWineDetail: LiveData<Wine>
        get() = _navigateToWineDetail

    private val _navigateToAddWine = MutableLiveData<Long>()
    val navigateToAddWine
        get() = _navigateToAddWine

    private val _navigateToMainFragment = MutableLiveData<Long>()
    val navigateToMainFragment
        get() = _navigateToMainFragment

    fun insert(wine: Wine) = database.insert(wine)

    fun deleteAllWines() = database.deleteAllWines()

    fun getAllWines(): LiveData<List<Wine>> {
        return allWines
    }

    fun initializeThisWine(id: Long) {
        uiScope.launch {
            particularWine.value = getWineFromDatabase(id)
        }
    }

    private suspend fun getWineFromDatabase(id: Long): Wine? {
        return withContext(Dispatchers.IO) {
            val wine = id.let { database.getWineDetails(it) }
            wine
        }
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

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}