package pl.nataliana.mywine.ui.detail

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.mywine.R
import com.example.mywine.databinding.FragmentWineDetailBinding
import kotlinx.coroutines.*
import pl.nataliana.mywine.adapter.WineAdapter
import pl.nataliana.mywine.adapter.WineListener
import pl.nataliana.mywine.database.WineDatabase
import pl.nataliana.mywine.model.WineDetailViewModel
import pl.nataliana.mywine.model.WineDetailViewModelFactory
import pl.nataliana.mywine.model.WinesListViewModel
import pl.nataliana.mywine.ui.main.MainFragmentDirections

class DetailFragment : Fragment() {

    private val uiScope = CoroutineScope(Dispatchers.Main)
    private val bgDispatcher: CoroutineDispatcher = Dispatchers.IO
    private lateinit var wineDetailViewModel: WineDetailViewModel
    private lateinit var detailAdapter: WineAdapter
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
        val viewModelFactory = WineDetailViewModelFactory(id, dataSource,binding)

        wineDetailViewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(WineDetailViewModel::class.java)

        binding.cardViewDetail.setOnClickListener {
            setClick(id)
        }
        binding.winesListViewModel = wineDetailViewModel
        binding.lifecycleOwner = this
        setHasOptionsMenu(true)

        activity?.title = getString(R.string.your_wine_details)

        return binding.root
    }

    private fun setClick(id: Long) {
        view?.findNavController()?.navigate(
            DetailFragmentDirections
                .actionDetailFragentToEditWineFragment(id)
        )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.detail_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.delete_this_wine -> {
                confirmThisWineDeletion()
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun confirmThisWineDeletion(): Boolean {
        showDeleteAlertDialog()
        return true
    }

    private fun showDeleteAlertDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setMessage(getString(R.string.alert_dialog_delete_this_wine))
        builder.setPositiveButton(android.R.string.ok) { _, _ ->
            uiScope.launch {
                async(bgDispatcher) {
                    // background thread
                    wineDetailViewModel.deleteThisWine()
                }
            }
            Toast.makeText(
                context,
                getString(R.string.wine_deleted_confirmation),
                Toast.LENGTH_LONG
            ).show()
            navBackToWinesList()
        }
        builder.setNegativeButton(android.R.string.cancel) { _, _ ->
            Toast.makeText(
                context,
                getString(R.string.wine_deleted_cancelled), Toast.LENGTH_SHORT
            ).show()
        }
        builder.show()
    }

    private fun navBackToWinesList() {
        view?.findNavController()
            ?.navigate(DetailFragmentDirections.actionDetailFragmentToMainFragment())
    }
}