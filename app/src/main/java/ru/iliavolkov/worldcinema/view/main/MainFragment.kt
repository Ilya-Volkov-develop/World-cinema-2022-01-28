package ru.iliavolkov.worldcinema.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.smarteist.autoimageslider.SliderView
import ru.iliavolkov.worldcinema.databinding.FragmentMainBinding
import ru.iliavolkov.worldcinema.model.CoverDTO
import ru.iliavolkov.worldcinema.model.FilmInfoDTO
import ru.iliavolkov.worldcinema.utils.IMAGE_URL
import ru.iliavolkov.worldcinema.viewmodel.AppStateInfo
import ru.iliavolkov.worldcinema.viewmodel.MainViewModel

class MainFragment:Fragment(),OnItemClickListener {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }
    private val adapter: MainFragmentAdapter by lazy { MainFragmentAdapter(this) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner, {renderData(it)})
        viewModel.getMoviesList("inTrend")
        binding.recyclerView.adapter = adapter
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position){
                    0-> {
                        viewModel.getMoviesList("inTrend")
                    }
                    1->{
                        viewModel.getMoviesList("new")
                    }
                    2->{
                        viewModel.getMoviesList("forMe")
                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}

        })
        init()

    }

    private fun init() {
        viewModel.getCover()
    }

    private fun renderData(it: Any?) {
        when(it){
            is CoverDTO->{
                initSlider(it.backgroundImage,it.foregroundImage)
            }
            is AppStateInfo.Success ->{
                adapter.setFilm(it.filmInfo)
            }
        }
    }

    private fun initSlider(backgroundImage: String, foregroundImage: String) {
        val imageSlider = binding.imageSlider
        val imageList: ArrayList<String> = ArrayList()
        imageList.add("$IMAGE_URL$foregroundImage")
        imageList.add("$IMAGE_URL$backgroundImage")
        val adapter = CoverSliderAdapter()
        adapter.renewItems(imageList)
        imageSlider.setSliderAdapter(adapter)
        imageSlider.isAutoCycle = true
        imageSlider.startAutoCycle()
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onItemClick(film: FilmInfoDTO) {

    }
}