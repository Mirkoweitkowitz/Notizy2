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
import de.syntaxinsitut.myapplication.databinding.FragmentEditNotizyBinding
import de.syntaxinsitut.myapplication.model.viewmodels.NoteViewModel

class EditNotizyFragment : Fragment() {

    private lateinit var binding: FragmentEditNotizyBinding
    private val viewModel: NoteViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditNotizyBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.colorPickerOrange.setOnClickListener {
            viewModel.setPickedColor(R.color.orange)
        }
        binding.colorPickerCyan.setOnClickListener {
            viewModel.setPickedColor(R.color.cyan)
        }
        binding.colorPickerGreen.setOnClickListener {
            viewModel.setPickedColor(R.color.green)
        }
        binding.colorPickerPurple.setOnClickListener {
            viewModel.setPickedColor(R.color.purple)
        }

        viewModel.currentSelectedNotizy.observe(
            viewLifecycleOwner,
            Observer {
                println(it)
                val colorDrawable = ColorDrawable()
                val colour = requireContext().getColor(it.color)
                colorDrawable.color = colour

                binding.colorChooserCl.background = colorDrawable
                binding.inputNote.background = colorDrawable
                binding.inputTitle.background = colorDrawable

                binding.inputTitle.setText(it.title)
                binding.inputNote.setText(it.note)
            }
        )

        viewModel.pickedColor.observe(
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

        binding.fbUpdateNotizy.setOnClickListener {

            val editNotizy = viewModel.currentSelectedNotizy.value
            editNotizy!!.note = binding.inputNote.text.toString()
            editNotizy!!.title = binding.inputTitle.text.toString()
            editNotizy!!.color = viewModel.pickedColor.value!!

            viewModel.updateNotizy(editNotizy)

            findNavController().navigateUp()
        }
    }
}
