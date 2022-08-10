package de.syntaxinsitut.myapplication.firebase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import de.syntaxinsitut.myapplication.R
import de.syntaxinsitut.myapplication.databinding.FragmentLoginUserBinding
import de.syntaxinsitut.myapplication.model.viewmodels.FireBaseViewModel


/**
 * SignUpFragment enthält das UI um einen neuen User zu registrieren
 */
class SignUpFragment: Fragment() {

    private val viewModel: FireBaseViewModel by activityViewModels()

    private lateinit var binding: FragmentLoginUserBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login_user, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.cancelButton.setOnClickListener{
            findNavController().navigateUp()
        }
        binding.signupButton.setOnClickListener{
            signUp()
        }

        viewModel.currentUser.observe(
            viewLifecycleOwner,
            Observer {
                if (it != null) {
                    findNavController().navigate(R.id.loginFragment)
                }
            }
        )
    }

    private fun signUp() {

        val email = binding.emailEdit.text.toString()
        val password = binding.passwordEdit.text.toString()

        if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
            viewModel.signUp(email, password)
        }
    }
}