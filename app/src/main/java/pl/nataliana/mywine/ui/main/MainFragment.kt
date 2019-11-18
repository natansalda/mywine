package pl.nataliana.mywine.ui.main

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.view.isEmpty
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.mywine.R
import com.example.mywine.databinding.FragmentMainBinding
import kotlinx.android.synthetic.main.fragment_main.*
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import pl.nataliana.mywine.adapter.WineAdapter
import pl.nataliana.mywine.adapter.WineListener
import pl.nataliana.mywine.database.WineDatabase
import pl.nataliana.mywine.model.Wine
import pl.nataliana.mywine.model.WinesListViewModel
import pl.nataliana.mywine.model.WinesListViewModelFactory
import pl.nataliana.mywine.ui.detail.AddWineFragment.Companion.EXTRA_COLOR
import pl.nataliana.mywine.ui.detail.AddWineFragment.Companion.EXTRA_NAME
import pl.nataliana.mywine.ui.detail.AddWineFragment.Companion.EXTRA_RATE
import pl.nataliana.mywine.ui.detail.AddWineFragment.Companion.EXTRA_YEAR

class MainFragment : Fragment() {

    private val wineViewModel: WinesListViewModel by inject()
    private val uiScope = CoroutineScope(Dispatchers.Main)
    private val bgDispatcher: CoroutineDispatcher = Dispatchers.IO

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
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(WinesListViewModel::class.java)

        binding.lifecycleOwner = this
        binding.winesListViewModel = wineViewModel
        binding.addWineButton.setOnClickListener {
            setupButtonAddWine()
        }

        val adapter = WineAdapter(WineListener { id ->
            Toast.makeText(context, "$id", Toast.LENGTH_LONG).show()
            wineViewModel.onWineClicked(id)
        })

        binding.recyclerView.adapter = adapter

        wineViewModel.getAllWines().observe(this,
            Observer<List<Wine>> { list ->
                list?.let {
                    adapter.submitList(it)
                }
            })

        // TODO add navigation
//        wineViewModel.navigateToWineDetail.observe(this, Observer {wine ->
//            wine?.let {
//                this.findNavController().navigate(MainActivityDirections
//                    .actionMainActivityToDetailActivity(wine))
//                wineViewModel.onWineDetailNavigated()
//            }
//        })

        setHasOptionsMenu(true)

        return binding.root
    }

    private fun setupButtonAddWine() {
        view?.findNavController()
            ?.navigate(MainFragmentDirections.actionMainFragentToAddWineFragment())
        // TODO how to retrieve data from AddWineFragment
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_all_wines -> {
                confirmDeletion()
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun confirmDeletion(): Boolean {
        showDeleteAlertDialog()
        return true
    }

    private fun showDeleteAlertDialog() {
        if (!recycler_view.isEmpty()) {
            val builder = AlertDialog.Builder(context)
            builder.setMessage(getString(R.string.alert_dialog_delete_wines))
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
            }
            builder.setNegativeButton(android.R.string.cancel) { _, _ ->
                Toast.makeText(
                    context,
                    getString(R.string.wines_deleted_cancelled), Toast.LENGTH_SHORT
                ).show()
            }
            builder.show()
        } else {
            Toast.makeText(
                context,
                getString(R.string.no_wines_yet), Toast.LENGTH_SHORT
            ).show()
        }
    }

    companion object{
        private const val ADD_WINE_REQUEST = 1
    }
}
