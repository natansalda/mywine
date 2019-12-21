package pl.nataliana.mywine.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import pl.nataliana.mywine.database.WineDatabaseDao
import pl.nataliana.mywine.databinding.FragmentWineDetailBinding

class WineDetailViewModelFactory(
    private val wineKey: Long,
    private val dataSource: WineDatabaseDao,
    val binding: FragmentWineDetailBinding
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WineDetailViewModel::class.java)) {
            return WineDetailViewModel(wineKey, dataSource,binding) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
