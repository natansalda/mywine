package pl.nataliana.mywine.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import pl.nataliana.mywine.R
import pl.nataliana.mywine.database.WineDatabase
import pl.nataliana.mywine.databinding.FragmentAddWineBinding
import pl.nataliana.mywine.model.Wine
import pl.nataliana.mywine.model.WinesListViewModel
import pl.nataliana.mywine.model.WinesListViewModel.Companion.sharedPref
import pl.nataliana.mywine.model.WinesListViewModelFactory
import pl.nataliana.mywine.util.WineHelper

class AddWineFragment : Fragment() {

    private var _binding: FragmentAddWineBinding? = null
    internal val binding: FragmentAddWineBinding
        get() = _binding ?: throw IllegalStateException()

    private lateinit var wineViewModel: WinesListViewModel
    private val uiScope = CoroutineScope(Dispatchers.Main)
    private val bgDispatcher: CoroutineDispatcher = Dispatchers.IO
    private var wineRating: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(
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
        setHasOptionsMenu(true)
        binding.addWineButton.setOnClickListener {
            saveWine()
        }

        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(true)

        return binding.root
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                view?.findNavController()?.navigateUp()
                true
            }

            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupGrapeRatingBar()
    }

    private fun setupGrapeRatingBar() {
        val grapeViews = listOf(
            binding.grape1, binding.grape2, binding.grape3,
            binding.grape4, binding.grape5
        )

        grapeViews.forEachIndexed { index, imageView ->
            imageView.setOnClickListener {
                setGrapeRating(index + 1, grapeViews)
            }
        }
    }

    private fun setGrapeRating(rating: Int, grapeViews: List<ImageView>) {
        wineRating = rating
        grapeViews.forEachIndexed { index, imageView ->
            imageView.setImageResource(
                if (index < rating) R.drawable.ic_grape_rate_icon_checked
                else R.drawable.ic_grape_rate_icon_unchecked
            )
        }
    }

    private fun saveWine() {
        if (checkIfNameNotEmpty() || checkIfColorNotEmpty()) {
            Toast.makeText(context, getString(R.string.wine_could_not_be_added), Toast.LENGTH_SHORT)
                .show()
            return
        } else {
            val data = applyWineData()
            val newWine = Wine(
                // we checked above that name and color are not empty
                data.getStringExtra(EXTRA_NAME)!!,
                data.getStringExtra(EXTRA_COLOR)!!,
                data.getIntExtra(EXTRA_YEAR, 0),
                data.getFloatExtra(EXTRA_RATE, 0F),
                data.getDoubleExtra(EXTRA_PRICE, 0.0),
                data.getStringExtra(EXTRA_TYPE)
            )

            uiScope.launch {
                async(bgDispatcher) {
                    // background thread
                    wineViewModel.insert(newWine)
                    WineHelper.PreferencesManager(sharedPref).saveWelcomeScreenStatus(false)
                }
                Toast.makeText(context, getString(R.string.wine_added_toast), Toast.LENGTH_SHORT)
                    .show()
            }
            view?.findNavController()
                ?.navigate(AddWineFragmentDirections.actionAddWineFragmentToMainFragment())
        }
    }

    private fun applyWineData(): Intent {
        return Intent().apply {
            val name = binding.editTextName.text.toString()
            val color = determineWineColor()
            val year: Int? =
                try {
                    Integer.valueOf(binding.editTextYear.text.toString())
                } catch (e: NumberFormatException) {
                    Integer.valueOf(0.toString())
                }
            val rating: Float = wineRating.toFloat()
            val price: Double =
                try {
                    binding.editTextPrice.text.toString().toDouble()
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
            binding.pinkRadioButton.isChecked -> getString(R.string.pink)
            binding.redRadioButton.isChecked -> getString(R.string.red)
            binding.whiteRadioButton.isChecked -> getString(R.string.white)
            // will never happen
            else -> null
        }
    }

    private fun checkIfNameNotEmpty(): Boolean {
        if (binding.editTextName.text.toString().trim().isBlank()) {
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

    override fun onDestroyView() {
        super.onDestroyView()
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(false)
        actionBar?.setDisplayShowHomeEnabled(false)
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
