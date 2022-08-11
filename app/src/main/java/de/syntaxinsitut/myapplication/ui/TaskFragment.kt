package de.syntaxinsitut.myapplication.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import de.syntaxinsitut.myapplication.R
import de.syntaxinsitut.myapplication.adapter.TaskGroupAdapter
import de.syntaxinsitut.myapplication.databinding.FragmentTaskBinding
import de.syntaxinsitut.myapplication.model.data.Notizy
import de.syntaxinsitut.myapplication.model.data.Task
import de.syntaxinsitut.myapplication.model.data.TaskGroup
import de.syntaxinsitut.myapplication.model.viewmodels.TaskViewModel
import java.util.*
import kotlin.collections.ArrayList

class TaskFragment : Fragment() {

    private lateinit var binding: FragmentTaskBinding
    private val vm: TaskViewModel by activityViewModels()
    private lateinit var adapter: TaskGroupAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm.taskGroupList.observe(
            viewLifecycleOwner,
            Observer {
                adapter = generateTaskGroupAdapter(it.toMutableList())
                binding.taskRecyclerView.adapter = adapter
            }
        )

        vm.taskList.observe(
            viewLifecycleOwner,
            Observer {
                try {
                    adapter.taskList = it
                    adapter.notifyDataSetChanged()
                }catch (ex:Exception){
                    Log.e("TaskFragment",ex.toString())
                }

            }

        )

        binding.taskRecyclerView.hasFixedSize()

        binding.newTaskButton.setOnClickListener {
            findNavController().navigate(R.id.newTaskFragment)
        }
        binding.taskEditText.addTextChangedListener {
            if(it.toString().isNullOrEmpty()){
                val adapter= generateTaskGroupAdapter(vm.taskGroupList.value!!.toMutableList())
                binding.taskRecyclerView.adapter = adapter
            } else {
                val filter = it.toString()
                val filterd = vm.taskGroupList.value!!.toMutableList().filter {
                    filter.uppercase() in it.title.uppercase()
                }

                val adapter = generateTaskGroupAdapter(filterd.toMutableList())
                binding.taskRecyclerView.adapter = adapter
            }

        }

    }

    fun generateTaskGroupAdapter(dataset:MutableList<TaskGroup>): TaskGroupAdapter{

        return TaskGroupAdapter(dataset,requireContext(), vm::deleteById, vm.taskList.value)

    }
}
