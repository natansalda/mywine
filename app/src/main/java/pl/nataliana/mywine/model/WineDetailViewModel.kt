package pl.nataliana.mywine.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mywine.databinding.FragmentWineDetailBinding
import kotlinx.coroutines.*
import pl.nataliana.mywine.database.WineDatabaseDao

class WineDetailViewModel(
    private val wineKey: Long = 0L,
    val database: WineDatabaseDao,
    private val binding: FragmentWineDetailBinding
) : ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    private var thisWine: Wine? = null

    private val _navigateToWineEdit = MutableLiveData<Wine>()
    val navigateToWineEdit: LiveData<Wine>
        get() = _navigateToWineEdit

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    init {
        initializeWine()
    }

    private fun initializeWine() {
        uiScope.launch {
            val wineFromDatabase = getWineFromDatabase()
            binding.wine = wineFromDatabase
        }
    }

    private suspend fun getWineFromDatabase(): Wine? {
        return withContext(Dispatchers.IO) {
            thisWine = database.getWineDetails(wineKey)
            thisWine
        }
    }

    fun onWineEditNavigated() {
        _navigateToWineEdit.value = null
    }

    fun deleteThisWine() = database.deleteThisWine(wineKey)
}