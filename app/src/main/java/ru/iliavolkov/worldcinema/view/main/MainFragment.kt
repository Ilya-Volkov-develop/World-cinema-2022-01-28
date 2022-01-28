package ru.iliavolkov.worldcinema.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.iliavolkov.worldcinema.databinding.FragmentMainBinding
import ru.iliavolkov.worldcinema.view.signscreenfragment.SignUpScreenFragment
import ru.iliavolkov.worldcinema.viewmodel.MainViewModel

class MainFragment:Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //viewModel.getLiveData().observe(viewLifecycleOwner, {renderData(it)})

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