package ru.iliavolkov.worldcinema.view.main.filminfo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import ru.iliavolkov.worldcinema.databinding.FragmentFilmImageBinding
import ru.iliavolkov.worldcinema.utils.BUNDLE_KEY_FILM_IMAGE
import ru.iliavolkov.worldcinema.utils.IMAGE_URL


class FilmImageFragment : Fragment() {

    private var _binding: FragmentFilmImageBinding? = null
    private val binding get() = _binding!!
    lateinit var imageUrl:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            it.getString(BUNDLE_KEY_FILM_IMAGE)?.let { imageString ->
                imageUrl = imageString
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFilmImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imageFullScreen.load("$IMAGE_URL${imageUrl}")
    }

    companion object {
        @JvmStatic
        fun newInstance(bundle: Bundle) = FilmImageFragment().apply { arguments = bundle }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}