package pl.nataliana.mywine.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.mywine.R
import com.example.mywine.databinding.FragmentWineDetailBinding
import pl.nataliana.mywine.database.WineDatabase
import pl.nataliana.mywine.model.WinesListViewModel
import pl.nataliana.mywine.model.WinesListViewModelFactory

class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentWineDetailBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_wine_detail, container, false
        )

        val application = requireNotNull(this.activity).application
//        val arguments = DetailFragmentArgs.fromBundle(arguments!!)

        val dataSource = WineDatabase.getInstance(application).wineDatabaseDao
        val viewModelFactory = WinesListViewModelFactory(dataSource, application)

        val wineListViewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(WinesListViewModel::class.java)

//        binding.winesListViewModel = wineListViewModel
        binding.lifecycleOwner = this

        // Add an Observer to the state variable for Navigating when a Quality icon is tapped.
//        wineListViewModel.navigateToSleepTracker.observe(this, Observer {
//            if (it == true) { // Observed state is true.
//                this.findNavController().navigate(
//                    SleepDetailFragmentDirections.actionSleepDetailFragmentToSleepTrackerFragment()
//                )
//                // Reset state to make sure we only navigate once, even if the device
//                // has a configuration change.
//                wineListViewModel.doneNavigating()
//            }
//        })

        return binding.root
    }
}