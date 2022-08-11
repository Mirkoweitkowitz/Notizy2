package de.syntaxinsitut.myapplication.ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import de.syntaxinsitut.myapplication.OnDragStartListener
import de.syntaxinsitut.myapplication.R
import de.syntaxinsitut.myapplication.adapter.NotizyAdapter
import de.syntaxinsitut.myapplication.adapter.TaskGroupAdapter
import de.syntaxinsitut.myapplication.databinding.FragmentNotizyBinding
import de.syntaxinsitut.myapplication.model.data.Notizy
import de.syntaxinsitut.myapplication.model.data.TaskGroup
import de.syntaxinsitut.myapplication.model.viewmodels.NoteViewModel

class NotizyFragment : Fragment() {

    private lateinit var binding: FragmentNotizyBinding
    private val viewModel: NoteViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotizyBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.newNotizButton.setOnClickListener {
            findNavController().navigate(R.id.newNotizyFragment)
        }




        viewModel.notizies.observe(
            viewLifecycleOwner,
            Observer {
                val adapter = generateNotizyAdapter(it.toMutableList())
                binding.notizyRecyclerView.adapter = adapter
            }
        )

        binding.notizyRecyclerView.hasFixedSize()

        binding.notesEditText.addTextChangedListener {
            if(it.toString().isNullOrEmpty()){
                val adapter= generateNotizyAdapter(viewModel.notizies.value!!.toMutableList())
                binding.notizyRecyclerView.adapter = adapter
            } else {
                val filter = it.toString()
                val filterd = viewModel.notizies.value!!.toMutableList().filter {
                    filter.uppercase() in it.title.uppercase()
                            || filter.uppercase() in it.note.uppercase()
                }

                val adapter = generateNotizyAdapter(filterd.toMutableList())
                binding.notizyRecyclerView.adapter = adapter
            }

        }
    }

    fun generateNotizyAdapter(dataset:MutableList<Notizy>): NotizyAdapter {
        val callback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN or
                    ItemTouchHelper.START or ItemTouchHelper.END, 0
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val adapter: NotizyAdapter = recyclerView.getAdapter() as NotizyAdapter
                val from = viewHolder.adapterPosition
                val to = target.adapterPosition
                adapter.moveNotizyViewItem(from, to)
                adapter.notifyItemMoved(from, to)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}

            @RequiresApi(api = Build.VERSION_CODES.M)
            override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
                super.onSelectedChanged(viewHolder, actionState)
                if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {

                }
            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            override fun clearView(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ) {
                super.clearView(recyclerView, viewHolder)

            }
        }
        val helper: ItemTouchHelper = ItemTouchHelper(callback)
        helper.attachToRecyclerView(binding.notizyRecyclerView)

        return NotizyAdapter(dataset,requireContext(), viewModel::deleteById, viewModel::setCurrentNotizy,object :
            OnDragStartListener {
            override fun onDragStart(holder: NotizyAdapter.NotizenViewHolder?) {
                helper.startDrag(holder!!)
            }
        })

    }
}
