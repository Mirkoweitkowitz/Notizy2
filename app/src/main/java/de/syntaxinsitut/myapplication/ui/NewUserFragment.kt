package de.syntaxinsitut.myapplication.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import de.syntaxinsitut.myapplication.R
import de.syntaxinsitut.myapplication.databinding.FragmentNewUserBinding
import de.syntaxinsitut.myapplication.model.data.User
import de.syntaxinsitut.myapplication.model.viewmodels.UserViewModel

class NewUserFragment : Fragment(){

    private val viewModel: UserViewModel by viewModels()

    private lateinit var binding: FragmentNewUserBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_new_user, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name = binding.nameEdit
        val mail = binding.mailEdit
        val addButton = binding.addButton

        addButton.setOnClickListener {
            val newUser = User(
                name.text.toString(),
                "female",
                mail.text.toString(),
                "active"
            )

            Log.e("NewUserfragment", "new user: $newUser")
            viewModel.addNewUser(newUser)

        }
    }
}