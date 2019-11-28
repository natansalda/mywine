package pl.nataliana.mywine.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.mywine.R
import com.example.mywine.databinding.FragmentWineDetailBinding
import pl.nataliana.mywine.database.WineDatabase
import pl.nataliana.mywine.model.WineDetailViewModel
import pl.nataliana.mywine.model.WineDetailViewModelFactory

class DetailFragment : Fragment() {

    lateinit var wineDetailViewModel: WineDetailViewModel
    var id: Long = 0L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentWineDetailBinding =
            DataBindingUtil.inflate(
                inflater, R.layout.fragment_wine_detail, container, false
            )

        val application = requireNotNull(this.activity).application
        val arguments = DetailFragmentArgs.fromBundle(arguments!!)
        id = arguments.id

        val dataSource = WineDatabase.getInstance(application).wineDatabaseDao
        val viewModelFactory = WineDetailViewModelFactory(id, dataSource)

        wineDetailViewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(WineDetailViewModel::class.java)

        binding.winesListViewModel = wineDetailViewModel
        binding.lifecycleOwner = this

        binding.wine = wineDetailViewModel.thisWine
        Log.d("Wine object in frag: ", binding.wine.toString())

        return binding.root
    }
}