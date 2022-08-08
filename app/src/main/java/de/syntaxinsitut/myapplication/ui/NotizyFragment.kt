package de.syntaxinsitut.myapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import de.syntaxinsitut.myapplication.R
import de.syntaxinsitut.myapplication.adapter.NotizyAdapter
import de.syntaxinsitut.myapplication.databinding.FragmentNotizyBinding
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
                val adapter = NotizyAdapter(it, requireContext(),  viewModel::deleteById, viewModel::setCurrentNotizy)
                binding.notizyRecyclerView.adapter = adapter
            }
        )

        binding.notizyRecyclerView.hasFixedSize()
    }
}
