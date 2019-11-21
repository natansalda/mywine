package pl.nataliana.mywine.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.example.mywine.R
import com.example.mywine.databinding.FragmentWineDetailBinding
import org.koin.android.ext.android.inject
import pl.nataliana.mywine.database.WineDatabase
import pl.nataliana.mywine.model.WinesListViewModel
import pl.nataliana.mywine.model.WinesListViewModelFactory

class DetailFragment : Fragment() {

    private val winesListViewModel: WinesListViewModel by inject()
    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val application = requireNotNull(this.activity).application
        val id = args.id

        winesListViewModel.getWineDetail(id)

        val dataSource = WineDatabase.getInstance(application).wineDatabaseDao
        val viewModelFactory = WinesListViewModelFactory(dataSource, application)

        val wineListViewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(WinesListViewModel::class.java)

        // TODO check binding with layout
        val binding: FragmentWineDetailBinding =
            DataBindingUtil.inflate(
                inflater, R.layout.fragment_wine_detail, container, false
            )

        binding.winesListViewModel = wineListViewModel
        binding.lifecycleOwner = this

        return binding.root
    }
}