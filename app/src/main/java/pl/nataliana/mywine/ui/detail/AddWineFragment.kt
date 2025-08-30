package pl.nataliana.mywine.ui.detail

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
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
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class AddWineFragment : Fragment() {

    private var _binding: FragmentAddWineBinding? = null
    internal val binding: FragmentAddWineBinding
        get() = _binding ?: throw IllegalStateException()

    private lateinit var wineViewModel: WinesListViewModel
    private val uiScope = CoroutineScope(Dispatchers.Main)
    private val bgDispatcher: CoroutineDispatcher = Dispatchers.IO
    private var wineRating: Int = 0
    private var photoURI: Uri? = null

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
        binding.addImageButton.setOnClickListener {
            selectImage()
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
                data.getStringExtra(EXTRA_TYPE),
                data.getStringExtra(EXTRA_PHOTO)
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
            val photo = photoURI?.toString()

            putExtra(EXTRA_NAME, name)
            putExtra(EXTRA_COLOR, color)
            putExtra(EXTRA_YEAR, year)
            putExtra(EXTRA_RATE, rating)
            putExtra(EXTRA_PRICE, price)
            putExtra(EXTRA_PHOTO, photo)
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

    private fun selectImage() {
        if (checkPermissions()) {
            val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Add a photo!")
            builder.setItems(options) { dialog, item ->
                when {
                    options[item] == "Take Photo" -> {
                        takePhoto()
                    }
                    options[item] == "Choose from Gallery" -> {
                        chooseFromGallery()
                    }
                    options[item] == "Cancel" -> {
                        dialog.dismiss()
                    }
                }
            }
            builder.show()
        } else {
            requestPermissions()
        }
    }

    private fun checkPermissions(): Boolean {
        val permissions = mutableListOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        if (requireActivity().packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            permissions.add(Manifest.permission.CAMERA)
        }
        return permissions.all {
            ContextCompat.checkSelfPermission(requireContext(), it) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun requestPermissions() {
        val permissions = mutableListOf(Manifest.permission.READ_EXTERNAL_STORAGE)
        if (requireActivity().packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            permissions.add(Manifest.permission.CAMERA)
        }
        requestPermissions(
            permissions.toTypedArray(),
            PERMISSION_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
                selectImage()
            } else {
                Toast.makeText(context, "Permissions denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun takePhoto() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    null
                }
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        requireContext(),
                        "pl.nataliana.mywine.fileprovider",
                        it
                    )
                    this.photoURI = photoURI
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(Date())
        val storageDir: File? = requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        )
    }


    private fun chooseFromGallery() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, REQUEST_GALLERY_PHOTO)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_IMAGE_CAPTURE -> {
                    binding.wineImage.setImageURI(photoURI)
                }
                REQUEST_GALLERY_PHOTO -> {
                    val selectedImageUri = data?.data
                    if (selectedImageUri != null) {
                        try {
                            val inputStream = requireActivity().contentResolver.openInputStream(selectedImageUri)
                            val file = createImageFile()
                            val outputStream = file.outputStream()
                            inputStream?.copyTo(outputStream)
                            inputStream?.close()
                            outputStream.close()
                            photoURI = FileProvider.getUriForFile(
                                requireContext(),
                                "pl.nataliana.mywine.fileprovider",
                                file
                            )
                            binding.wineImage.setImageURI(photoURI)
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        }
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
        const val EXTRA_PHOTO = "pl.nataliana.mywine.EXTRA_PHOTO"
        private const val REQUEST_IMAGE_CAPTURE = 1
        private const val REQUEST_GALLERY_PHOTO = 2
        private const val PERMISSION_REQUEST_CODE = 101
    }
}
