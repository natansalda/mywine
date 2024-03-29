package pl.nataliana.mywine.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import kotlinx.coroutines.*
import pl.nataliana.mywine.R
import pl.nataliana.mywine.database.WineDatabase
import pl.nataliana.mywine.databinding.FragmentEditWineBinding
import pl.nataliana.mywine.model.Wine
import pl.nataliana.mywine.model.WineEditViewModel
import pl.nataliana.mywine.model.WineEditViewModelFactory

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class EditWineFragment : Fragment() {

    private var _binding: FragmentEditWineBinding? = null
    internal val binding: FragmentEditWineBinding
        get() = _binding ?: throw IllegalStateException()

    // TODO check what's needed in this fragment
    private lateinit var editWineViewModel: WineEditViewModel
    private val uiScope = CoroutineScope(Dispatchers.Main)
    private val bgDispatcher: CoroutineDispatcher = Dispatchers.IO
    var id: Long = 0L

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_edit_wine, container, false
        )

        activity?.title = getString(R.string.edit_wine)

        val application = requireNotNull(this.activity).application
        val arguments = DetailFragmentArgs.fromBundle(requireArguments())
        id = arguments.id

        val dataSource = WineDatabase.getInstance(application).wineDatabaseDao
        val viewModelFactory = WineEditViewModelFactory(id, dataSource, binding)

        editWineViewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(WineEditViewModel::class.java)

        binding.wineEditViewModel = editWineViewModel
        binding.lifecycleOwner = this

        binding.addWineButton.text = getString(R.string.save_changes_after_edit)
        binding.addWineButton.setOnClickListener {
            saveWine()
        }

        return binding.root
    }

    private fun saveWine() {

        if (checkIfNameNotEmpty() || checkIfColorNotEmpty()) {
            Toast.makeText(context, getString(R.string.wine_could_not_be_added), Toast.LENGTH_SHORT)
                .show()
            return
        } else {
            val data = applyWineData()
            val updatedWine = Wine(
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
                    editWineViewModel.edit(updatedWine)
                }
                Toast.makeText(context, getString(R.string.wine_edited), Toast.LENGTH_SHORT).show()
            }
            view?.findNavController()
                ?.navigate(EditWineFragmentDirections.actionEditWineFragmentToMainFragment())
        }
    }

    private fun applyWineData(): Intent {
        return Intent().apply {
            val name = binding.nameEdit.text.toString()
            val color = determineWineColor()
            val year = binding.yearEdit.text?.toString()?.let { Integer.valueOf(it) }
            val rating = binding.rateEdit.toString().let { Integer.valueOf(it) }
            val price = binding.priceEdit.text?.toString()?.let { Integer.valueOf(it) }
            val type = determineWineType()

            putExtra(EXTRA_NAME, name)
            putExtra(EXTRA_COLOR, color)
            putExtra(EXTRA_YEAR, year)
            putExtra(EXTRA_RATE, rating)
            putExtra(EXTRA_PRICE, price)
            putExtra(EXTRA_TYPE, type)
        }
    }

    private fun determineWineColor(): String {
        return when {
            binding.colorPinkEdit.isChecked -> getString(R.string.pink)
            binding.colorRedEdit.isChecked -> getString(R.string.red)
            else -> getString(R.string.white)
        }
    }

    private fun determineWineType(): String? {
        return when {
            binding.dryRadioButton.isChecked -> "dry"
            binding.semiDryRadioButton.isChecked -> "semi dry"
            binding.sweetRadioButton.isChecked -> "sweet"
            else -> "semi sweet"
        }
    }

    private fun checkIfNameNotEmpty(): Boolean {
        if (binding.nameEdit.text.toString().trim().isBlank()) {
            Toast.makeText(context, getString(R.string.cant_set_empty_record), Toast.LENGTH_LONG)
                .show()
            return true
        }
        return false
    }

    private fun checkIfColorNotEmpty(): Boolean {
        return false
    }

    // TODO ensure that back button takes us to proper wine

    companion object {
        const val EXTRA_NAME = "pl.nataliana.mywine.EXTRA_NAME"
        const val EXTRA_COLOR = "pl.nataliana.mywine.EXTRA_COLOR"
        const val EXTRA_YEAR = "pl.nataliana.mywine.EXTRA_YEAR"
        const val EXTRA_RATE = "pl.nataliana.mywine.EXTRA_RATE"
        const val EXTRA_PRICE = "pl.nataliana.mywine.EXTRA_PRICE"
        const val EXTRA_TYPE = "pl.nataliana.mywine.EXTRA_TYPE"
    }
}
