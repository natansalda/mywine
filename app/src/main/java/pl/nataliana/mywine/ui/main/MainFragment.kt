package pl.nataliana.mywine.ui.main

import android.app.AlertDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.isEmpty
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import pl.nataliana.mywine.R
import pl.nataliana.mywine.adapter.WineAdapter
import pl.nataliana.mywine.adapter.WineListener
import pl.nataliana.mywine.database.WineDatabase
import pl.nataliana.mywine.databinding.FragmentMainBinding
import pl.nataliana.mywine.model.Wine
import pl.nataliana.mywine.model.WinesListViewModel
import pl.nataliana.mywine.model.WinesListViewModelFactory


class MainFragment : Fragment() {

    private val wineViewModel: WinesListViewModel by inject()
    private val uiScope = CoroutineScope(Dispatchers.Main)
    private val bgDispatcher: CoroutineDispatcher = Dispatchers.IO
    private var winesSortedBest = false
    private lateinit var mainAdapter: WineAdapter
    private var privateMode = 0
    private val prefName = "mindorks-welcome"
    private var sharedPref: SharedPreferences? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentMainBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        val application = requireNotNull(this.activity).application
        val dataSource = WineDatabase.getInstance(application).wineDatabaseDao
        val viewModelFactory = WinesListViewModelFactory(dataSource, application)

        val wineViewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(WinesListViewModel::class.java)

        binding.lifecycleOwner = this
        binding.winesListViewModel = wineViewModel
        binding.addWineButton.setOnClickListener {
            setupButtonAddWine()
        }

        mainAdapter = WineAdapter(WineListener { id -> setClick(id) })
        showListOfWinesInChronologicalOrder()
        binding.recyclerView.adapter = mainAdapter
        setHasOptionsMenu(true)

        activity?.title = getString(R.string.app_name)

        return binding.root
    }

    override fun onResume() {
        super.onResume()

        sharedPref = activity?.getSharedPreferences(prefName, privateMode)

        if (sharedPref!!.getBoolean(prefName, false)) {
            instruction_layout.visibility = View.GONE
        } else {
            instruction_layout.visibility = View.VISIBLE
            val editor = sharedPref!!.edit()
            editor.putBoolean(prefName, true)
            editor.apply()
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
                sortWines()
            }
            R.id.reset_to_chronological_order -> {
                showListOfWinesInChronologicalOrder()
                winesSortedBest = false
                return winesSortedBest
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun showListOfWinesInChronologicalOrder() {
        wineViewModel.getAllWines().observe(viewLifecycleOwner,
            Observer<List<Wine>> { list ->
                list?.let {
                    mainAdapter.submitList(it)
                }
            })

        Toast.makeText(
            context,
            getString(R.string.wines_sorted_chronological_order), Toast.LENGTH_SHORT
        ).show()
    }

    private fun sortWines(): Boolean {
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

        Toast.makeText(
            context,
            getString(R.string.wines_sorted_rate_order), Toast.LENGTH_SHORT
        ).show()

        return winesSortedBest
    }

    private fun confirmDeletion(): Boolean {
        showDeleteAlertDialog()
        return true
    }

    private fun showDeleteAlertDialog() {
        if (!recycler_view.isEmpty()) {
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
            instruction_layout.visibility = View.VISIBLE
            val editor = sharedPref!!.edit()
            editor.putBoolean(prefName, true)
            editor.apply()
        }
    }
}
