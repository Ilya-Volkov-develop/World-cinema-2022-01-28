package ru.iliavolkov.worldcinema.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.iliavolkov.worldcinema.view.history.HistoryFragment
import ru.iliavolkov.worldcinema.R
import ru.iliavolkov.worldcinema.databinding.FragmentMainBinding
import ru.iliavolkov.worldcinema.view.main.compilationscreen.CompilationsFragment
import ru.iliavolkov.worldcinema.view.main.mainscreen.MainNavigationFragment

@Suppress("DEPRECATION")
class MainFragment:Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initBottomNavigation()
    }

    private fun init() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentMainContainer,MainNavigationFragment.newInstance())
            .commit()
    }

    private fun initBottomNavigation() {
        binding.bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.page_1 -> {
                    init()
                    true
                }
                R.id.page_2 -> {
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentMainContainer,CompilationsFragment.newInstance())
                        .commit()
                    true
                }
                R.id.page_3 -> {
                    requireActivity().supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentMainContainer, HistoryFragment.newInstance())
                        .commit()
                    true
                }
                R.id.page_4 -> {
                    init()
                    true
                }
                else -> false
            }
        }
        binding.bottomNavigationView.setOnNavigationItemReselectedListener { item ->
            when(item.itemId) {
                R.id.page_1 -> { true }
                R.id.page_2 -> { true }
                R.id.page_3 -> { true }
                R.id.page_4 -> { true }
                else -> false
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}