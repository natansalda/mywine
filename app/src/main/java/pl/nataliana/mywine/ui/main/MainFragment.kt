package pl.nataliana.mywine.ui.main

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.view.isEmpty
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import kotlinx.coroutines.*
import pl.nataliana.mywine.R
import pl.nataliana.mywine.adapter.WineAdapter
import pl.nataliana.mywine.adapter.WineListener
import pl.nataliana.mywine.database.WineDatabase
import pl.nataliana.mywine.databinding.FragmentMainBinding
import pl.nataliana.mywine.model.Wine
import pl.nataliana.mywine.model.WinesListViewModel
import pl.nataliana.mywine.model.WinesListViewModel.Companion.sharedPref
import pl.nataliana.mywine.model.WinesListViewModelFactory
import pl.nataliana.mywine.util.WineHelper
import pl.nataliana.mywine.util.WineHelper.PreferencesManager.Companion.WELCOME_SCREEN_PREF

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    internal val binding: FragmentMainBinding
        get() = _binding ?: throw IllegalStateException()

    private lateinit var wineViewModel: WinesListViewModel
    private val uiScope = CoroutineScope(Dispatchers.Main)
    private val bgDispatcher: CoroutineDispatcher = Dispatchers.IO
    private var winesSortedBest = false
    private lateinit var mainAdapter: WineAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = WineDatabase.getInstance(application).wineDatabaseDao
        val viewModelFactory = WinesListViewModelFactory(dataSource, application)

        wineViewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(WinesListViewModel::class.java)

        binding.lifecycleOwner = this
        binding.winesListViewModel = wineViewModel
        binding.addWineButton.setOnClickListener {
            setupButtonAddWine()
        }

        mainAdapter = WineAdapter(WineListener { id -> setClick(id) })
        displayWines()
        binding.recyclerView.adapter = mainAdapter
        setHasOptionsMenu(true)

        activity?.title = getString(R.string.app_name)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPref = activity?.getSharedPreferences(WELCOME_SCREEN_PREF, Context.MODE_PRIVATE)
        checkPreferences()
    }

    override fun onResume() {
        super.onResume()
        checkPreferences()
        Log.w("MainFragment: ", "onResume")
    }

    private fun checkPreferences() {

        if (WineHelper.PreferencesManager(sharedPref).checkWelcomeScreenStatus() == false) {
            binding.instructionLayout.visibility = View.GONE
            Log.w("MainFragment: ", "view gone")
        } else {
            binding.instructionLayout.visibility = View.VISIBLE
            Log.w("MainFragment: ", "view visible")
        }
    }

    private fun setClick(id: Long) {
        view?.findNavController()?.navigate(
            MainFragmentDirections
                .actionMainFragmentToDetailFragment(id)
        )
        wineViewModel.onWineDetailNavigated()
    }

    private fun setupButtonAddWine() {
        view?.findNavController()
            ?.navigate(MainFragmentDirections.actionMainFragentToAddWineFragment())
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_all_wines -> {
                confirmDeletion()
            }
            R.id.sorting_wines -> {
                showToastThatWinesAreSortedByRating()
                sortWinesByRating()
            }
            R.id.reset_to_chronological_order -> {
                showListOfWinesInChronologicalOrder()
                if (WineHelper.PreferencesManager(sharedPref).checkWelcomeScreenStatus() == false) {
                    showToastThatWinesAreSortedChronologically()
                }
                winesSortedBest = false
                return winesSortedBest
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun displayWines() {
        // TODO here check what order method was selected last time
        showListOfWinesInChronologicalOrder()
    }

    private fun showListOfWinesInChronologicalOrder() {
        wineViewModel.getAllWines().observe(viewLifecycleOwner,
            Observer<List<Wine>> { list ->
                list?.let {
                    mainAdapter.submitList(it)
                }
            })
    }

    private fun showToastThatWinesAreSortedChronologically() {
        Toast.makeText(
            context,
            getString(R.string.wines_sorted_chronological_order), Toast.LENGTH_SHORT
        ).show()
    }

    private fun showToastThatWinesAreSortedByRating() {
        Toast.makeText(
            context,
            getString(R.string.wines_sorted_rate_order), Toast.LENGTH_SHORT
        ).show()
    }

    private fun sortWinesByRating(): Boolean {
        if (!winesSortedBest) {
            wineViewModel.getAllWinesByRatingBest().observe(this,
                Observer<List<Wine>> { list ->
                    list?.let {
                        mainAdapter.submitList(it)
                    }
                })
            winesSortedBest = true
        } else {
            wineViewModel.getAllWinesByRatingWorse().observe(this,
                Observer<List<Wine>> { list ->
                    list?.let {
                        mainAdapter.submitList(it)
                    }
                })
            winesSortedBest = false
        }

        return winesSortedBest
    }

    private fun confirmDeletion(): Boolean {
        showDeleteAlertDialog()
        return true
    }

    private fun showDeleteAlertDialog() {
        if (!binding.recyclerView.isEmpty()) {
            val builder = AlertDialog.Builder(context)
            builder.setMessage(getString(R.string.alert_dialog_delete_wines))
            setPositiveButton(builder)
            setNegativeButton(builder)
            builder.show()
        } else {
            Toast.makeText(
                context,
                getString(R.string.no_wines_yet), Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun setNegativeButton(builder: AlertDialog.Builder) {
        builder.setNegativeButton(android.R.string.cancel) { _, _ ->
            Toast.makeText(
                context,
                getString(R.string.wines_deleted_cancelled), Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun setPositiveButton(builder: AlertDialog.Builder) {
        builder.setPositiveButton(android.R.string.ok) { _, _ ->
            uiScope.launch {
                async(bgDispatcher) {
                    // background thread
                    wineViewModel.deleteAllWines()
                }
            }
            Toast.makeText(
                context,
                getString(R.string.wines_deleted_confirmation),
                Toast.LENGTH_LONG
            ).show()
            WineHelper.PreferencesManager(sharedPref).saveWelcomeScreenStatus(true)
            checkPreferences()
        }
    }
}
