package ru.iliavolkov.worldcinema.view.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.iliavolkov.worldcinema.R
import ru.iliavolkov.worldcinema.databinding.FragmentHistoryBinding
import ru.iliavolkov.worldcinema.model.FilmInfoDTO
import ru.iliavolkov.worldcinema.utils.BUNDLE_KEY_FILM_INFO
import ru.iliavolkov.worldcinema.view.main.filminfo.FilmFragment
import ru.iliavolkov.worldcinema.view.main.mainscreen.OnItemClickListener
import ru.iliavolkov.worldcinema.viewmodel.AppStateBD
import ru.iliavolkov.worldcinema.viewmodel.HistoryViewModel

class HistoryFragment : Fragment(), OnItemClickListener {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private val adapter: FilmsHistoryAdapter by lazy { FilmsHistoryAdapter(this) }
    private val viewModel: HistoryViewModel by lazy { ViewModelProvider(this).get(HistoryViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHistoryBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.historyFragmentRecyclerview.adapter = adapter
        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getAllHistory()
    }

    private fun renderData(appStateBD: AppStateBD) {
        when(appStateBD){
            is AppStateBD.Error ->{}
            is AppStateBD.Success ->{
                adapter.setWeather(appStateBD.weatherInfoHistoryData)
            }
            else -> {}
        }

    }
    override fun onItemClick(film: FilmInfoDTO) {
        requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.container, FilmFragment.newInstance(Bundle().apply {
                    putParcelable(BUNDLE_KEY_FILM_INFO,film)
                }))
                .addToBackStack("")
                .commit()
    }

    companion object {
        @JvmStatic
        fun newInstance() = HistoryFragment()
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}