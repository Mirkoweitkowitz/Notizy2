package de.syntaxinsitut.myapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import de.syntaxinsitut.myapplication.adapter.TaskAdapter
import de.syntaxinsitut.myapplication.databinding.FragmentTaskDetailBinding
import de.syntaxinsitut.myapplication.model.viewmodels.TaskViewModel

class TaskDetailFragment : Fragment() {

    private lateinit var binding: FragmentTaskDetailBinding
    private val viewModel: TaskViewModel by activityViewModels()
    private var id: Long = 0
    private var title: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getLong("id")
            title = it.getString("title")!!
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.taskList.observe(
            viewLifecycleOwner,
            Observer {
                val filtered = it.filter {
                    it.groupId == id
                }
                val adapter = TaskAdapter(filtered, requireContext(), viewModel::updateTask)
                binding.detailRv.adapter = adapter
            }

        )

        binding.detailTaskTitleTv.text = title
    }
}
