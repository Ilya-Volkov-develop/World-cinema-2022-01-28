package ru.iliavolkov.worldcinema.view.main.filminfo

import android.annotation.SuppressLint
import android.app.Activity
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import ru.iliavolkov.worldcinema.R
import ru.iliavolkov.worldcinema.databinding.FragmentFilmBinding
import ru.iliavolkov.worldcinema.model.FilmInfoDTO
import ru.iliavolkov.worldcinema.repositiry.RepositoriesRoomImpl
import ru.iliavolkov.worldcinema.utils.BUNDLE_KEY_FILM_IMAGE
import ru.iliavolkov.worldcinema.utils.BUNDLE_KEY_FILM_INFO
import ru.iliavolkov.worldcinema.utils.IMAGE_URL
import ru.iliavolkov.worldcinema.utils.VIDEO_URL
import ru.iliavolkov.worldcinema.viewmodel.AppStateEpisodes
import ru.iliavolkov.worldcinema.viewmodel.MainViewModel

@Suppress("DEPRECATION")
class FilmFragment:Fragment(),OnItemClickListener {

    lateinit var mediaController: MediaController

    private var _binding: FragmentFilmBinding? = null
    private val binding get() = _binding!!
    lateinit var film:FilmInfoDTO
    private val adapter: FilmFragmentFramesAdapter by lazy { FilmFragmentFramesAdapter(this) }
    private val repositoriesRoomImpl: RepositoriesRoomImpl by lazy { RepositoriesRoomImpl() }
    private val viewModel: MainViewModel by lazy { ViewModelProvider(this).get(MainViewModel::class.java) }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFilmBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mediaController = MediaController(requireContext())
        viewModel.getLiveData().observe(viewLifecycleOwner, {renderData(it)})
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
        binding.frames.adapter = adapter
        init()
        favoriteBtn()
    }

    private fun renderData(appState: Any?) {

        mediaController.setAnchorView(binding.filmVideo)
        when(appState){
            is AppStateEpisodes.Success ->{
                if (appState.filmInfo.isNotEmpty()){
                    binding.filmVideo.setVideoURI(Uri.parse("$VIDEO_URL${appState.filmInfo[0].preview}"))
                    binding.filmVideo.setMediaController(mediaController)
                    binding.filmVideo.start()
                } else {
                    binding.filmVideo.setVideoURI(Uri.parse("https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"))
                    binding.filmVideo.setMediaController(mediaController)
                    binding.filmVideo.start()
                }
            }
        }
    }

    private fun favoriteBtn() {
        var isFavorite = requireActivity().getPreferences(Activity.MODE_PRIVATE).getBoolean("isFavorite${film.movieID}",false)
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
                requireActivity().getPreferences(Activity.MODE_PRIVATE).edit().putBoolean("isFavorite${film.movieID}",isFavorite).apply()
            } else {
                repositoriesRoomImpl.deleteFilm(film)
                binding.favorite.background = requireActivity().getDrawable(R.drawable.ic_baseline_favorite_border_24)
                isFavorite = !isFavorite
                requireActivity().getPreferences(Activity.MODE_PRIVATE).edit().putBoolean("isFavorite${film.movieID}",isFavorite).apply()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun init() {
        with(binding){
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


    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle) = FilmFragment().apply { arguments = bundle }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onItemClick(frame:String) {
        requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.container,FilmImageFragment.newInstance(Bundle().apply {
                    putString(BUNDLE_KEY_FILM_IMAGE,frame)
                }))
                .addToBackStack("")
                .commit()
    }
}