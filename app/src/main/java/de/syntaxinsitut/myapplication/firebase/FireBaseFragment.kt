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

class FireBaseFragment : Fragment(){

    private lateinit var binding:FragmentLoginUserBinding

    private val viewModel: FireBaseViewModel by activityViewModels()


    private lateinit var userMail: String


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

        viewModel.currentUser.observe(
            viewLifecycleOwner,
            Observer {
                if (it == null) {
                    findNavController().navigate(R.id.loginFragment)
                } else {
                    userMail = it.email.toString()

                }
            }
        )

        binding.logoutButton.setOnClickListener {
//            viewModel.logout()
        }
    }
}