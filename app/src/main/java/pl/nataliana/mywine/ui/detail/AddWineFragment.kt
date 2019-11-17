package pl.nataliana.mywine.ui.detail

import android.app.Activity
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
import com.example.mywine.databinding.FragmentAddWineBinding
import kotlinx.android.synthetic.main.fragment_add_wine.*
import pl.nataliana.mywine.database.WineDatabase
import pl.nataliana.mywine.model.WinesListViewModel
import pl.nataliana.mywine.model.WinesListViewModelFactory

class AddWineFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentAddWineBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_add_wine, container, false
        )

        activity?.title = getString(R.string.add_new_wine)

        val application = requireNotNull(this.activity).application

        val dataSource = WineDatabase.getInstance(application).wineDatabaseDao
        val viewModelFactory = WinesListViewModelFactory(dataSource, application)

        val wineViewModel =
            ViewModelProviders.of(
                this, viewModelFactory
            ).get(WinesListViewModel::class.java)

        binding.winesListViewModel = wineViewModel
        binding.lifecycleOwner = this

        binding.addWineButton.setOnClickListener {
            saveWine()
        }

        return binding.root
    }

    private fun saveWine() {
        if (checkIfNameNotEmpty()) return
        if (checkIfColorNotEmpty()) return

        val data = applyWineData()

        activity?.run {
            setResult(Activity.RESULT_OK, data)
        }
        view?.findNavController()?.navigate(R.id.action_addWineFragment_to_mainFragment)
    }

    private fun applyWineData(): Intent {
        return Intent().apply {
            val name = edit_text_name.text.toString()
            val color = determineWineColor()
            val year: Int = Integer.valueOf(edit_text_year.text.toString())
            // TODO add price to view
            val rating: Int = Integer.valueOf(edit_text_rate.text.toString())

            putExtra(EXTRA_NAME, name)
            putExtra(EXTRA_COLOR, color)
            putExtra(EXTRA_YEAR, year)
            putExtra(EXTRA_RATE, rating)
        }
    }

    private fun determineWineColor(): String? {
        return when {
            pink_radio_button.isChecked ->getString(R.string.pink)
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
        const val EXTRA_NAME = "com.example.mywine.EXTRA_NAME"
        const val EXTRA_COLOR = "com.example.mywine.EXTRA_COLOR"
        const val EXTRA_YEAR = "com.example.mywine.EXTRA_YEAR"
        const val EXTRA_RATE = "com.example.mywine.EXTRA_RATE"
    }
}