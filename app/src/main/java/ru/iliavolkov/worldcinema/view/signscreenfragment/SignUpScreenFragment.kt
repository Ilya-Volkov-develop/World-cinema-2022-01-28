package ru.iliavolkov.worldcinema.view.signscreenfragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.iliavolkov.worldcinema.R
import ru.iliavolkov.worldcinema.databinding.FragmentSignUpScreenBinding
import ru.iliavolkov.worldcinema.model.TokenDTO
import ru.iliavolkov.worldcinema.viewmodel.MainViewModel

class SignUpScreenFragment:Fragment() {

    private var _binding: FragmentSignUpScreenBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSignUpScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner, {renderData(it)})
        binding.btnRegistration.setOnClickListener { btnSignUp() }
        binding.btnHaveAccount.setOnClickListener { btnSignIn() }
    }

    private fun renderData(it: Any?) {
        when(it){
            is String->{
                val email = binding.signUpEmail.text.toString()
                val password = binding.signUpPassword.text.toString()
                viewModel.signIn(email, password)
            }
            is TokenDTO ->{

            }
        }
        Log.d("myLog",it.toString())
    }

    private fun btnSignUp() {
        val email = binding.signUpEmail.text.toString()
        val password = binding.signUpPassword.text.toString()
        val firstName = binding.signUpName.text.toString()
        val lastName = binding.signUpFamily.text.toString()
        viewModel.signUp(email,password,firstName,lastName)
    }

    private fun btnSignIn() {
        requireActivity().supportFragmentManager.beginTransaction().replace(
            R.id.container, SignInScreenFragment.newInstance()
        ).commit()
    }

    companion object {
        @JvmStatic
        fun newInstance() = SignUpScreenFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}