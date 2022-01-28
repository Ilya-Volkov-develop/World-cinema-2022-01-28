package ru.iliavolkov.worldcinema.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.smarteist.autoimageslider.SliderView
import ru.iliavolkov.worldcinema.databinding.FragmentMainBinding
import ru.iliavolkov.worldcinema.model.CoverDTO
import ru.iliavolkov.worldcinema.utils.IMAGE_URL
import ru.iliavolkov.worldcinema.viewmodel.MainViewModel

class MainFragment:Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)


        return binding.root
    }

    private fun initSlider(backgroundImage: String, foregroundImage: String) {
        val imageSlider = binding.imageSlider
        val imageList: ArrayList<String> = ArrayList()
        imageList.add("$IMAGE_URL$foregroundImage")
        imageList.add("$IMAGE_URL$backgroundImage")
        setImageInSlider(imageList, imageSlider)
    }

    private fun setImageInSlider(images: ArrayList<String>, imageSlider: SliderView) {
        val adapter = CoverSliderAdapter()
        adapter.renewItems(images)
        imageSlider.setSliderAdapter(adapter)
        imageSlider.isAutoCycle = true
        imageSlider.startAutoCycle()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner, {renderData(it)})
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