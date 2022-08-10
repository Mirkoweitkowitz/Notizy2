package de.syntaxinsitut.myapplication.firebase

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import de.syntaxinsitut.myapplication.R
import de.syntaxinsitut.myapplication.adapter.UserAdapter
import de.syntaxinsitut.myapplication.databinding.FragmentUserBinding
import de.syntaxinsitut.myapplication.model.data.User
import de.syntaxinsitut.myapplication.model.viewmodels.UserViewModel

class UserFragment : Fragment(){

    private val viewModel: UserViewModel by viewModels()

    private lateinit var binding: FragmentUserBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        Log.e("VIEW", "OncreateVIEW")
        viewModel.loadData()
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_login_user, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userList = binding.userList
        val addUser = binding.addFab




        userList.setHasFixedSize(true)

        addUser.setOnClickListener {
            findNavController().navigate(R.id.action_fireBaseFragment_to_userFragment)
        }

        viewModel.users.observe(
            viewLifecycleOwner,
            Observer {
                userList.adapter = UserAdapter(it)
            }
        )


    }


}