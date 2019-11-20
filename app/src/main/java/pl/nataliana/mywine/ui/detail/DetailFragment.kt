package pl.nataliana.mywine.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.mywine.R
import com.example.mywine.databinding.FragmentWineDetailBinding
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.android.ext.android.inject
import pl.nataliana.mywine.database.WineDatabase
import pl.nataliana.mywine.model.WinesListViewModel
import pl.nataliana.mywine.model.WinesListViewModelFactory

class DetailFragment : Fragment() {

    private val winesListViewModel: WinesListViewModel by inject()
    private var viewModelJob = Job()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // TODO check binding with layout
        val binding: FragmentWineDetailBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_wine_detail, container, false
        )

        val application = requireNotNull(this.activity).application
        val arguments = DetailFragmentArgs.fromBundle(arguments!!)

        val dataSource = WineDatabase.getInstance(application).wineDatabaseDao
        val viewModelFactory = WinesListViewModelFactory(dataSource, application)

        val wineListViewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(WinesListViewModel::class.java)

        binding.winesListViewModel = wineListViewModel
        binding.lifecycleOwner = this

        wineListViewModel.getAllWines().observe(this, Observer {
            this.findNavController().navigate(
                DetailFragmentDirections.actionDetailFragmentToMainFragment()
            )
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        winesListViewModel.getWineDetail(id.toLong())
//        GlobalScope.launch {
//            withContext(Dispatchers.Main) {
//                winesListViewModel.getWineDetail(id.toLong())
//            }
//        }
    }
}