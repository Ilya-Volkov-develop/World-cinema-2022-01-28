package ru.iliavolkov.worldcinema.view.main.filminfo

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import ru.iliavolkov.worldcinema.databinding.FragmentFilmBinding
import ru.iliavolkov.worldcinema.model.FilmInfoDTO
import ru.iliavolkov.worldcinema.utils.BUNDLE_KEY_FILM_INFO
import ru.iliavolkov.worldcinema.utils.IMAGE_URL
import ru.iliavolkov.worldcinema.view.main.filminfo.OnItemClickListener

@Suppress("DEPRECATION")
class FilmFragment:Fragment(),OnItemClickListener {

    private var _binding: FragmentFilmBinding? = null
    private val binding get() = _binding!!
    lateinit var film:FilmInfoDTO
    private val adapter: FilmFragmentFramesAdapter by lazy { FilmFragmentFramesAdapter(this) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFilmBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
        binding.frames.adapter = adapter
        init()
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

    override fun onItemClick() {

    }
}