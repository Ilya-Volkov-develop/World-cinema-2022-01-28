package ru.iliavolkov.worldcinema.view.main.mainscreen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.media_controller.*
import ru.iliavolkov.worldcinema.R
import ru.iliavolkov.worldcinema.databinding.FragmentMainNavigationBinding
import ru.iliavolkov.worldcinema.model.CoverDTO
import ru.iliavolkov.worldcinema.model.FilmInfoDTO
import ru.iliavolkov.worldcinema.room.App
import ru.iliavolkov.worldcinema.room.App.Companion.initPlayer
import ru.iliavolkov.worldcinema.room.App.Companion.loadVod
import ru.iliavolkov.worldcinema.utils.BUNDLE_KEY_FILM_INFO
import ru.iliavolkov.worldcinema.utils.IMAGE_URL
import ru.iliavolkov.worldcinema.view.main.filminfo.FilmFragment
import ru.iliavolkov.worldcinema.viewmodel.AppStateEpisodes
import ru.iliavolkov.worldcinema.viewmodel.AppStateInfo
import ru.iliavolkov.worldcinema.viewmodel.HistoryViewModel
import ru.iliavolkov.worldcinema.viewmodel.MainViewModel

class MainNavigationFragment:Fragment(), OnItemClickListener {

    private var _binding: FragmentMainNavigationBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }
    private val adapter: MainFragmentAdapter by lazy { MainFragmentAdapter(this) }
    private val roomViewModel: HistoryViewModel by lazy { ViewModelProvider(this).get(HistoryViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentMainNavigationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner, {renderData(it)})
        viewModel.getCover()
        roomViewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
        roomViewModel.getEpisode()
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
        forward.setOnClickListener{ App.player?.seekTo(App.player!!.currentPosition + 10*1000) }
        replay.setOnClickListener{ App.player?.seekTo(App.player!!.currentPosition - 10*1000) }
    }

    private fun renderData(it: Any?) {
        when(it){
            is CoverDTO ->{
                initSlider(it.backgroundImage,it.foregroundImage)
                viewModel.getMoviesList("inTrend")
            }
            is AppStateInfo.Success ->{
                adapter.setFilm(it.filmInfo)
            }
            is AppStateEpisodes.SuccessRoom -> {
                binding.youWatchedContainer.visibility = View.VISIBLE
                initPlayer(binding.playerView,requireContext())
                loadVod(it.episodePath,requireContext())
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

    override fun onItemClick(film: FilmInfoDTO) {
        requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container,FilmFragment.newInstance(Bundle().apply {
                    putParcelable(BUNDLE_KEY_FILM_INFO,film)
                }))
                .addToBackStack("")
                .commit()
        App.player?.let {
            it.release()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = MainNavigationFragment()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        App.player?.let {
            it.release()
        }
    }
}