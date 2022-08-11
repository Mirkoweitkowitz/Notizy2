package de.syntaxinsitut.myapplication.ui

import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import de.syntaxinsitut.myapplication.R
import de.syntaxinsitut.myapplication.adapter.ScanAdapter
import de.syntaxinsitut.myapplication.databinding.FragmentScanBinding
import de.syntaxinsitut.myapplication.model.viewmodels.ScanViewModel



// das ScanFragment


class ScanFragment : Fragment() {

    private lateinit var binding: FragmentScanBinding
    private val viewModel: ScanViewModel by activityViewModels()
    val REQUEST_IMAGE_CAPTURE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val takePhotoIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        binding.newScanButton.setOnClickListener {
            try {
                startActivityForResult(takePhotoIntent, REQUEST_IMAGE_CAPTURE)
            } catch (ex: Exception) {
                Log.e("CAM", ex.toString())
            }
            true
        }

        viewModel.scanList.observe(
            viewLifecycleOwner,
            Observer {
                println(it)
                val adapter = ScanAdapter(it, requireContext(), viewModel::downloadImage)
                binding.scansRecyclerView.adapter = adapter
            }
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            try {
                val imageBitmap = data?.extras?.get("data") as Bitmap
                viewModel.setCurrentBitmap(imageBitmap)
                findNavController().navigate(R.id.newScanFragment)
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "Something went wrong: $e", Toast.LENGTH_LONG).show()
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}
