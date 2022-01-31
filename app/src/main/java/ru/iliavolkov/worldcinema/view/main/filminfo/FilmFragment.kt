package ru.iliavolkov.worldcinema.view.main.filminfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_film.*
import ru.iliavolkov.worldcinema.R
import ru.iliavolkov.worldcinema.databinding.FragmentFilmBinding
import ru.iliavolkov.worldcinema.databinding.FragmentMainBinding
import ru.iliavolkov.worldcinema.model.CoverDTO
import ru.iliavolkov.worldcinema.model.FilmInfoDTO
import ru.iliavolkov.worldcinema.utils.BUNDLE_KEY_FILM_INFO
import ru.iliavolkov.worldcinema.utils.IMAGE_URL
import ru.iliavolkov.worldcinema.view.main.mainscreen.MainFragmentAdapter
import ru.iliavolkov.worldcinema.view.main.mainscreen.MainNavigationFragment
import ru.iliavolkov.worldcinema.view.main.mainscreen.OnItemClickListener
import ru.iliavolkov.worldcinema.viewmodel.AppStateInfo
import ru.iliavolkov.worldcinema.viewmodel.MainViewModel

@Suppress("DEPRECATION")
class FilmFragment:Fragment() {

    private var _binding: FragmentFilmBinding? = null
    private val binding get() = _binding!!
    lateinit var film:FilmInfoDTO

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
        init()
    }

    private fun init() {
        with(binding){
            filmName.text = film.name
            age.text = "${film.age}+"
            filmPreview.load("$IMAGE_URL${film.poster}")
            filmDescription.text = film.description
            film.images?.forEach {

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
}