package de.syntaxinsitut.myapplication.ui

import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import de.syntaxinsitut.myapplication.R
import de.syntaxinsitut.myapplication.databinding.FragmentNewTaskBinding
import de.syntaxinsitut.myapplication.model.data.Task
import de.syntaxinsitut.myapplication.model.data.TaskGroup
import de.syntaxinsitut.myapplication.model.viewmodels.TaskViewModel
import kotlinx.coroutines.launch

class NewTaskFragment : Fragment() {

    private lateinit var binding: FragmentNewTaskBinding
    private val viewModel: TaskViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editFields = mutableListOf<EditText>()

        viewModel.setPickedColor(R.color.orange)

        binding.newTaskButton.setOnClickListener {
            val editText = EditText(requireContext())
            editText.hint = "Typein a Task Description"
            editFields.add(editText)
            binding.svLl.addView(editText)
        }

        binding.saveTaskButton.setOnClickListener {

            if (editFields.isEmpty()) {
                Toast.makeText(requireContext(), "You have to add at least 1 New Task", Toast.LENGTH_LONG).show()
            } else {
                val taskGroup = TaskGroup(
                    title = binding.etTitleTask.text.toString(),
                    color = viewModel.pickedColor.value!!
                )

                viewModel.insertTaskGroup(taskGroup)

                val taskInsertJob = lifecycleScope.launch() {
                    viewModel.insertTaskGroup.join()
                    for (editText in editFields) {
                        val task = Task(
                            task = editText.text.toString(),
                            groupId = viewModel.currentInsertedId.value!!,
                            done = false
                        )

                        viewModel.insertTask(task)
                    }
                }

                lifecycleScope.launch {
                    taskInsertJob.join()
                    findNavController().navigate(R.id.taskFragment)
                }
            }
        }

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

        viewModel.pickedColor.observe(
            viewLifecycleOwner,
            Observer {
                val colorDrawable = ColorDrawable()
                val colour = requireContext().getColor(it)
                colorDrawable.color = colour
                binding.etTitleTask.background = colorDrawable
                binding.newTaskScrollView.background = colorDrawable
                binding.svLl.background = colorDrawable
                binding.colorChooserCl.background = colorDrawable
            }
        )
    }
}
