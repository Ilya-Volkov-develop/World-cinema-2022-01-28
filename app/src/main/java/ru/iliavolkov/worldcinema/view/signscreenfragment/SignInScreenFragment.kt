package ru.iliavolkov.worldcinema.view.signscreenfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.iliavolkov.worldcinema.R
import ru.iliavolkov.worldcinema.databinding.FragmentSignInScreenBinding
import ru.iliavolkov.worldcinema.model.TokenDTO
import ru.iliavolkov.worldcinema.viewmodel.MainViewModel

class SignInScreenFragment:Fragment() {

    private var _binding: FragmentSignInScreenBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSignInScreenBinding.inflate(inflater, container, false)
        viewModel.getLiveData().observe(viewLifecycleOwner, {renderData(it)})
        return binding.root
    }

    private fun renderData(it: Any?) {
        if (it is TokenDTO){
            requireActivity().supportFragmentManager.beginTransaction().replace(
                R.id.container, SignInScreenFragment.newInstance()
            ).commit()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonSignIn.setOnClickListener { signIn() }
        binding.buttonSignUp.setOnClickListener { signUp() }
    }

    private fun signIn() {
        val email = binding.signInEmail.text.toString()
        val password = binding.signInPassword.text.toString()
        viewModel.signIn(email,password)
    }

    private fun signUp() {
        requireActivity().supportFragmentManager.beginTransaction().replace(
            R.id.container,
            SignUpScreenFragment.newInstance()
        ).commit()
    }

    companion object {
        @JvmStatic
        fun newInstance() = SignInScreenFragment()
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}