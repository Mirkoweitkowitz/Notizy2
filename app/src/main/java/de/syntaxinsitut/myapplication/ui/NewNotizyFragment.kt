package de.syntaxinsitut.myapplication.ui

import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import de.syntaxinsitut.myapplication.R
import de.syntaxinsitut.myapplication.databinding.FragmentNewNotizyBinding
import de.syntaxinsitut.myapplication.model.data.Notizy
import de.syntaxinsitut.myapplication.model.viewmodels.NoteViewModel

class NewNotizyFragment : Fragment() {

    private lateinit var binding: FragmentNewNotizyBinding
    private val vm: NoteViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNewNotizyBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.setPickedColor(R.color.orange)

        binding.colorPickerOrange.setOnClickListener {
            vm.setPickedColor(R.color.orange)
        }
        binding.colorPickerCyan.setOnClickListener {
            vm.setPickedColor(R.color.cyan)
        }
        binding.colorPickerGreen.setOnClickListener {
            vm.setPickedColor(R.color.green)
        }
        binding.colorPickerPurple.setOnClickListener {
            vm.setPickedColor(R.color.purple)
        }

        binding.fbSaveNotizy.setOnClickListener {
            val notizy = Notizy(
                title = binding.inputTitle.text.toString(),
                note = binding.inputNote.text.toString(),
                color = vm.pickedColor.value!!
            )

            vm.insertNotizy(notizy)
            findNavController().navigate(R.id.notizyFragment)
        }

        vm.pickedColor.observe(
            viewLifecycleOwner,
            Observer {
                val colorDrawable = ColorDrawable()
                val colour = requireContext().getColor(it)
                colorDrawable.color = colour
                binding.colorChooserCl.background = colorDrawable
                binding.inputNote.background = colorDrawable
                binding.inputTitle.background = colorDrawable
            }
        )
    }
}
