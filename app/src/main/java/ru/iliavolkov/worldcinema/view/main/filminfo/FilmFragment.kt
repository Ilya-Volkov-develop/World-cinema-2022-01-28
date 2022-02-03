package ru.iliavolkov.worldcinema.view.main.filminfo

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import kotlinx.android.synthetic.main.media_controller.*
import ru.iliavolkov.worldcinema.R
import ru.iliavolkov.worldcinema.databinding.FragmentFilmBinding
import ru.iliavolkov.worldcinema.model.FilmInfoDTO
import ru.iliavolkov.worldcinema.repositiry.RepositoriesRoomImpl
import ru.iliavolkov.worldcinema.room.App
import ru.iliavolkov.worldcinema.room.App.Companion.initPlayer
import ru.iliavolkov.worldcinema.room.App.Companion.loadVod
import ru.iliavolkov.worldcinema.utils.BUNDLE_KEY_FILM_IMAGE
import ru.iliavolkov.worldcinema.utils.BUNDLE_KEY_FILM_INFO
import ru.iliavolkov.worldcinema.utils.IMAGE_URL
import ru.iliavolkov.worldcinema.utils.VIDEO_URL
import ru.iliavolkov.worldcinema.viewmodel.AppStateEpisodes
import ru.iliavolkov.worldcinema.viewmodel.MainViewModel

@Suppress("DEPRECATION")
@SuppressLint("SetTextI18n")
class FilmFragment:Fragment(),OnItemClickListener {

    private var _binding: FragmentFilmBinding? = null
    private val binding get() = _binding!!
    private lateinit var film:FilmInfoDTO
    private val adapter: FilmFragmentFramesAdapter by lazy { FilmFragmentFramesAdapter(this) }
    private val repositoriesRoomImpl: RepositoriesRoomImpl by lazy { RepositoriesRoomImpl() }
    private val viewModel: MainViewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFilmBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
        arguments?.let {
            it.getParcelable<FilmInfoDTO>(BUNDLE_KEY_FILM_INFO)?.let { filmInfo ->
                film = FilmInfoDTO(
                        filmInfo.movieID,
                        filmInfo.name,
                        filmInfo.description,
                        filmInfo.age,
                        filmInfo.images,
                        filmInfo.poster,
                        filmInfo.tags
                )
            }
        }
        viewModel.getEpisodes(film.movieID)
        init()
        favoriteBtn()
        initPlayer(binding.playerView,requireContext())
        forward.setOnClickListener{ App.player?.seekTo(App.player!!.currentPosition + 10*1000) }
        replay.setOnClickListener{ App.player?.seekTo(App.player!!.currentPosition - 10*1000) }
    }

    private fun init() {
        with(binding){
            frames.adapter = adapter
            filmName.text = film.name
            age.text = "${film.age}+"
            filmPreview.load("$IMAGE_URL${film.poster}")
            filmDescription.text = film.description
            if (film.images.isNotEmpty()){
                adapter.setFrames(film.images)
                frameContainer.visibility = View.VISIBLE
            }
        }
    }
    private fun favoriteBtn() {
        var isFavorite = requireActivity().getPreferences(Activity.MODE_PRIVATE).getBoolean("isFavorite${film.movieID}", false)
        if (isFavorite) {
            binding.favorite.background = requireActivity().getDrawable(R.drawable.ic_baseline_favorite_24)
        }  else {
            binding.favorite.background = requireActivity().getDrawable(R.drawable.ic_baseline_favorite_border_24)
        }
        binding.favorite.setOnClickListener {
            if (!isFavorite){
                repositoriesRoomImpl.saveFilm(film)
                binding.favorite.background = requireActivity().getDrawable(R.drawable.ic_baseline_favorite_24)
                isFavorite = !isFavorite
                requireActivity().getPreferences(Activity.MODE_PRIVATE).edit().putBoolean("isFavorite${film.movieID}", isFavorite).apply()
            } else {
                repositoriesRoomImpl.deleteFilm(film)
                binding.favorite.background = requireActivity().getDrawable(R.drawable.ic_baseline_favorite_border_24)
                isFavorite = !isFavorite
                requireActivity().getPreferences(Activity.MODE_PRIVATE).edit().putBoolean("isFavorite${film.movieID}", isFavorite).apply()
            }
        }
    }
    private fun renderData(appState: Any?) {
        when(appState){
            is AppStateEpisodes.Success -> {
                if (appState.filmInfo.isNotEmpty()) {
                    loadVod("$VIDEO_URL${appState.filmInfo[0].preview}",requireContext())
                    repositoriesRoomImpl.saveEpisodePath("$VIDEO_URL${appState.filmInfo[0].preview}")
                } else {
                    loadVod("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",requireContext())
                    repositoriesRoomImpl.saveEpisodePath("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4")
                }
            }
        }
    }
    override fun onItemClick(frame: String) {
        requireActivity().supportFragmentManager.beginTransaction()
            .add(R.id.container, FilmImageFragment.newInstance(Bundle().apply {
                putString(BUNDLE_KEY_FILM_IMAGE, frame)
            }))
            .addToBackStack("")
            .commit()
    }

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle) = FilmFragment().apply { arguments = bundle }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        App.player!!.release()
    }

}