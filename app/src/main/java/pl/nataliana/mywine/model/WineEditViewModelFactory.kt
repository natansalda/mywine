package pl.nataliana.mywine.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mywine.databinding.FragmentEditWineBinding
import com.example.mywine.databinding.FragmentWineDetailBinding
import pl.nataliana.mywine.database.WineDatabaseDao

class WineEditViewModelFactory(
    private val wineKey: Long,
    private val dataSource: WineDatabaseDao,
    val binding: FragmentEditWineBinding
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WineEditViewModel::class.java)) {
            return WineEditViewModel(wineKey, dataSource,binding) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
