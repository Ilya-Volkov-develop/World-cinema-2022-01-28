package ru.iliavolkov.worldcinema.view.splash

import android.app.Activity
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.iliavolkov.worldcinema.R
import ru.iliavolkov.worldcinema.databinding.FragmentSplashBinding
import ru.iliavolkov.worldcinema.view.signscreenfragment.SignInScreenFragment
import ru.iliavolkov.worldcinema.view.signscreenfragment.SignUpScreenFragment

class SplashFragment: Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPreferences:SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        sharedPreferences = requireActivity().getSharedPreferences("first_launch",Activity.MODE_PRIVATE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler().postDelayed({ start() }, 2000)
    }

    private fun start() {
        val firstLaunch: Boolean = sharedPreferences.getBoolean("first_launch", true)
        if (firstLaunch) {
            sharedPreferences.edit().putBoolean("first_launch", false).apply()
            requireActivity().supportFragmentManager.beginTransaction().replace(
                R.id.container,
                SignUpScreenFragment.newInstance()
            ).commit()

        } else {
            requireActivity().supportFragmentManager.beginTransaction().replace(
                R.id.container,
                SignInScreenFragment.newInstance()
            ).commit()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = SplashFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}