package pl.nataliana.mywine.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.example.mywine.R
import com.example.mywine.databinding.FragmentEditWineBinding
import kotlinx.android.synthetic.main.fragment_add_wine.*
import kotlinx.coroutines.*
import org.koin.android.ext.android.inject
import pl.nataliana.mywine.database.WineDatabase
import pl.nataliana.mywine.model.Wine
import pl.nataliana.mywine.model.WineDetailViewModel
import pl.nataliana.mywine.model.WineEditViewModel
import pl.nataliana.mywine.model.WineEditViewModelFactory

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class EditWineFragment : Fragment() {

    // TODO check what's needed in this fragment
    private val editWineViewModel: WineDetailViewModel by inject()
    private val uiScope = CoroutineScope(Dispatchers.Main)
    private val bgDispatcher: CoroutineDispatcher = Dispatchers.IO
    var id: Long = 0L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentEditWineBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_edit_wine, container, false
        )

        activity?.title = getString(R.string.edit_wine)

        val application = requireNotNull(this.activity).application
        val arguments = DetailFragmentArgs.fromBundle(arguments!!)
        id = arguments.id

        val dataSource = WineDatabase.getInstance(application).wineDatabaseDao
        val viewModelFactory = WineEditViewModelFactory(id, dataSource, binding)

        val wineViewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(WineEditViewModel::class.java)

        binding.wineEditViewModel = wineViewModel
        binding.lifecycleOwner = this

        binding.addWineButton.text = getString(R.string.save_changes_after_edit)
        binding.addWineButton.setOnClickListener {
            saveWine()
        }

        return binding.root
    }

    private fun saveWine() {
        if (!checkIfRateInBounds()) {
            Toast.makeText(
                context,
                getString(R.string.rate_needs_to_be_in_bounds),
                Toast.LENGTH_SHORT
            )
                .show()
            return
        }

        if (checkIfNameNotEmpty() || checkIfColorNotEmpty()) {
            Toast.makeText(context, getString(R.string.wine_could_not_be_added), Toast.LENGTH_SHORT)
                .show()
            return
        }


        val data = applyWineData()
        val updatedWine = Wine(
            data.getStringExtra(EXTRA_NAME),
            data.getStringExtra(EXTRA_COLOR),
            data.getIntExtra(EXTRA_YEAR, 0),
            data.getIntExtra(EXTRA_RATE, 0),
            data.getIntExtra(EXTRA_PRICE, 0)
        )

        uiScope.launch {
            async(bgDispatcher) {
                // background thread
                editWineViewModel.edit(updatedWine)
            }
            Toast.makeText(context, getString(R.string.wine_edited), Toast.LENGTH_SHORT).show()
        }
        view?.findNavController()
            ?.navigate(EditWineFragmentDirections.actionEditWineFragmentToMainFragment())
    }

    private fun applyWineData(): Intent {
        return Intent().apply {
            val name = edit_text_name.text.toString()
            val color = determineWineColor()
            val year: Int = Integer.valueOf(edit_text_year.text.toString())
            val rating: Int = Integer.valueOf(edit_text_rate.text.toString())
            val price: Int = Integer.valueOf(edit_text_price.text.toString())

            putExtra(EXTRA_NAME, name)
            putExtra(EXTRA_COLOR, color)
            putExtra(EXTRA_YEAR, year)
            putExtra(EXTRA_RATE, rating)
            putExtra(EXTRA_PRICE, price)
        }
    }

    private fun determineWineColor(): String? {
        return when {
            pink_radio_button.isChecked -> getString(R.string.pink)
            red_radio_button.isChecked -> getString(R.string.red)
            white_radio_button.isChecked -> getString(R.string.white)
            // will never happen
            else -> null
        }
    }

    private fun checkIfNameNotEmpty(): Boolean {
        if (edit_text_name.text.toString().trim().isBlank()) {
            Toast.makeText(context, getString(R.string.cant_set_empty_record), Toast.LENGTH_LONG)
                .show()
            return true
        }
        return false
    }

    private fun checkIfColorNotEmpty(): Boolean {
        if (determineWineColor() == null) {
            Toast.makeText(context, getString(R.string.cant_set_empty_record), Toast.LENGTH_LONG)
                .show()
            return true
        }
        return false
    }

    private fun checkIfRateInBounds(): Boolean {
        val rateInEditText = edit_text_rate.text.toString().toInt()
        return if (rateInEditText in 1..5) {
            true
        } else {
            Toast.makeText(
                context,
                getString(R.string.rate_needs_to_be_in_bounds),
                Toast.LENGTH_LONG
            )
                .show()
            false
        }
    }

    // TODO ensure that back button takes us to proper wine

    companion object {
        const val EXTRA_NAME = "com.example.mywine.EXTRA_NAME"
        const val EXTRA_COLOR = "com.example.mywine.EXTRA_COLOR"
        const val EXTRA_YEAR = "com.example.mywine.EXTRA_YEAR"
        const val EXTRA_RATE = "com.example.mywine.EXTRA_RATE"
        const val EXTRA_PRICE = "com.example.mywine.EXTRA_PRICE"
    }
}