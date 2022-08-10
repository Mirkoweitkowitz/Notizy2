package de.syntaxinsitut.myapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import de.syntaxinsitut.myapplication.databinding.FragmentNewScanBinding
import de.syntaxinsitut.myapplication.model.viewmodels.ScanViewModel
import okhttp3.internal.wait

class NewScanFragment : Fragment() {

    private lateinit var binding: FragmentNewScanBinding
    private val viewModel: ScanViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewScanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.currentImageBitmap.observe(
            viewLifecycleOwner,
            Observer {
                binding.scanImagePreview.setImageBitmap(it)
            }
        )

        binding.saveScanButton.setOnClickListener {
            if (binding.scanTitleInput.text.toString() != "") {
                viewModel.upload(binding.scanTitleInput.text.toString())
                findNavController().navigateUp()
            } else {
                Toast.makeText(requireContext(), "Titel", Toast.LENGTH_LONG).show()
            }
        }
    }
}
