package pl.nataliana.mywine.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar.OnRatingBarChangeListener
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_add_wine.*
import kotlinx.coroutines.*
import pl.nataliana.mywine.R
import pl.nataliana.mywine.database.WineDatabase
import pl.nataliana.mywine.databinding.FragmentAddWineBinding
import pl.nataliana.mywine.model.Wine
import pl.nataliana.mywine.model.WinesListViewModel
import pl.nataliana.mywine.model.WinesListViewModelFactory

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class AddWineFragment : Fragment() {

    private lateinit var wineViewModel: WinesListViewModel
    private val uiScope = CoroutineScope(Dispatchers.Main)
    private val bgDispatcher: CoroutineDispatcher = Dispatchers.IO
    private var wineRating: Float = 0F

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentAddWineBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_add_wine, container, false
        )

        activity?.title = getString(R.string.add_new_wine)

        val application = requireNotNull(this.activity).application

        val dataSource = WineDatabase.getInstance(application).wineDatabaseDao
        val viewModelFactory = WinesListViewModelFactory(dataSource, application)

        wineViewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(WinesListViewModel::class.java)

        binding.winesListViewModel = wineViewModel
        binding.lifecycleOwner = this

        binding.addWineButton.setOnClickListener {
            saveWine()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        addListenerOnRatingBar()
    }

    private fun addListenerOnRatingBar() {

        rating_bar.onRatingBarChangeListener =
            OnRatingBarChangeListener { _, rating, _ ->
                wineRating = rating
            }
    }

    private fun saveWine() {

        if (checkIfNameNotEmpty() || checkIfColorNotEmpty()) {
            Toast.makeText(context, getString(R.string.wine_could_not_be_added), Toast.LENGTH_SHORT)
                .show()
            return
        }

        val data = applyWineData()
        val newWine = Wine(
            data.getStringExtra(EXTRA_NAME),
            data.getStringExtra(EXTRA_COLOR),
            data.getIntExtra(EXTRA_YEAR, 0),
            data.getFloatExtra(EXTRA_RATE, 0F),
            data.getDoubleExtra(EXTRA_PRICE, 0.0),
            data.getStringExtra(EXTRA_TYPE)
        )

        uiScope.launch {
            async(bgDispatcher) {
                // background thread
                wineViewModel.insert(newWine)
            }
            Toast.makeText(context, getString(R.string.wine_added_toast), Toast.LENGTH_SHORT).show()
        }
        view?.findNavController()
            ?.navigate(AddWineFragmentDirections.actionAddWineFragmentToMainFragment())
    }

    private fun applyWineData(): Intent {
        return Intent().apply {
            val name = edit_text_name.text.toString()
            val color = determineWineColor()
            val year: Int? =
                try {
                    Integer.valueOf(edit_text_year.text.toString())
                } catch (e: NumberFormatException) {
                    Integer.valueOf(0.toString())
                }
            val rating: Float? = wineRating
            val price: Double? =
                try {
                    edit_text_price.text.toString().toDouble()
                } catch (e: NumberFormatException) {
                    0.toString().toDouble()
                }

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

    companion object {
        const val EXTRA_NAME = "pl.nataliana.mywine.EXTRA_NAME"
        const val EXTRA_COLOR = "pl.nataliana.mywine.EXTRA_COLOR"
        const val EXTRA_YEAR = "pl.nataliana.mywine.EXTRA_YEAR"
        const val EXTRA_RATE = "pl.nataliana.mywine.EXTRA_RATE"
        const val EXTRA_PRICE = "pl.nataliana.mywine.EXTRA_PRICE"
        const val EXTRA_TYPE = "pl.nataliana.mywine.EXTRA_TYPE"
    }
}
