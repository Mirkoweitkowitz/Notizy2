package de.syntaxinsitut.myapplication.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import de.syntaxinsitut.myapplication.R
import de.syntaxinsitut.myapplication.adapter.TaskGroupAdapter
import de.syntaxinsitut.myapplication.databinding.FragmentTaskBinding
import de.syntaxinsitut.myapplication.model.data.Notizy
import de.syntaxinsitut.myapplication.model.data.Task
import de.syntaxinsitut.myapplication.model.viewmodels.TaskViewModel
import java.util.*
import kotlin.collections.ArrayList

class TaskFragment : Fragment() {

    private lateinit var binding: FragmentTaskBinding
    private val vm: TaskViewModel by activityViewModels()
    private lateinit var adapter: TaskGroupAdapter
//    var notes = List<Task>(),

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
                adapter = TaskGroupAdapter(it, requireContext(), vm::deleteById, vm.taskList.value)
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

//        binding.task_edit_text.setOnQueryTextListener(object :
//            SearchView.OnQueryTextListener,
//            androidx.appcompat.widget.SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(p0: String?): Boolean {
//                return true
//            }
//
//            override fun onQueryTextChange(p0: String?): Boolean {
//
//                val tempArr = List<Task>()
//
//                for (arr in notes) {
//                    if (arr.title!!.toLowerCase(Locale.getDefault()).contains(p0.toString())) {
//                        tempArr.add(arr)
//                    }
//                }
//                adapter.setData(tempArr)
//                adapter.notifyDataSetChanged()
//                return true
//            }
//        })
    }
}
