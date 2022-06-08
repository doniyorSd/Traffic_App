package com.example.trafficapp

import android.net.Uri
import android.os.Bundle
import android.os.Environment
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.navigation.fragment.findNavController
import com.example.trafficapp.database.TrafficDatabase
import com.example.trafficapp.databinding.FragmentAddTrafficBinding
import com.example.trafficapp.databinding.ItemDialogBinding
import com.example.trafficapp.entity.Traffic
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class AddTrafficFragment : Fragment() {
    lateinit var photoUri: Uri
    lateinit var imageFile: File
    lateinit var currentPhotoPath: String
    lateinit var bind: FragmentAddTrafficBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_add_traffic, container, false)
        val list = arrayListOf("Ogohlantiruvchi", "Imtiyozli", "Ta'qiqlovchi", "Buyuruvchi")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, list)
        bind = FragmentAddTrafficBinding.bind(view)
        bind.type.adapter = adapter

        bind.iv.setOnClickListener {
            val dialog = AlertDialog.Builder(requireContext()).create()
            val myView = LayoutInflater.from(requireContext())
                .inflate(R.layout.item_dialog, null, false)
            val binding = ItemDialogBinding.bind(myView)

            binding.ivCamera.setOnClickListener {
                getImageFromCameraNewVersion()
                dialog.dismiss()
            }
            binding.ivGallery.setOnClickListener {
                getImageFromGallery()
                dialog.dismiss()
            }
            dialog.setView(myView)
            dialog.show()
        }
        val trafficDatabase = TrafficDatabase.getInstance(requireContext())
        bind.btn.setOnClickListener {
            val name = bind.name.text.toString()
            val description = bind.description.text.toString()
            val type = bind.type.selectedItem.toString()
            val traffic = Traffic(currentPhotoPath, name, description, type, false)
            trafficDatabase.trafficDao().insertTraffic(traffic)
            findNavController().popBackStack()
        }
        return view
    }

    private fun getImageFromCameraNewVersion() {
        imageFile = createImageFile()
        photoUri =
            FileProvider.getUriForFile(requireContext(), BuildConfig.APPLICATION_ID, imageFile)
        getTakeImageContent.launch(photoUri)
    }

    private var getTakeImageContent =
        registerForActivityResult(ActivityResultContracts.TakePicture()) {
            if (it) {
                bind.iv.setImageURI(photoUri)
            }
        }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String =
            SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir = requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)

        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }

    private fun getImageFromGallery() {
        getImageContent.launch("image/*")
    }

    private var getImageContent = registerForActivityResult(ActivityResultContracts.GetContent()) {
        it ?: return@registerForActivityResult
        bind.iv.setImageURI(it)
        // uri
        val openInputStream = requireContext().contentResolver?.openInputStream(it)
        val file = File(requireContext().filesDir, "${it.hashCode()}.jpg")
        val fileOutputStream = FileOutputStream(file)
        openInputStream?.copyTo(fileOutputStream)
        openInputStream?.close()
        fileOutputStream.close()

        currentPhotoPath = file.absolutePath
    }
}