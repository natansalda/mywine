package pl.nataliana.mywine.model

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import pl.nataliana.mywine.database.WineDatabaseDao

class WineDetailViewModel(
    private val wineKey: Long = 0L,
    val database: WineDatabaseDao
) : ViewModel() {

    private val viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun onWineDetailDisplay(id: Long) {
        uiScope.launch {
            // IO is a thread pool for running operations that access the disk, such as
            // our Room database.
            withContext(Dispatchers.IO) {
                val thisWine = database.getWineDetails(wineKey) ?: return@withContext
                thisWine.id = id
                database.getWineDetails(id)
            }
        }
    }
}