package ru.iliavolkov.worldcinema.view.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.iliavolkov.worldcinema.R
import ru.iliavolkov.worldcinema.databinding.FragmentMainRecyclerFilmItemBinding
import ru.iliavolkov.worldcinema.model.FilmInfoDTO
import ru.iliavolkov.worldcinema.utils.IMAGE_URL
import ru.iliavolkov.worldcinema.view.main.mainscreen.OnItemClickListener

class FilmsHistoryAdapter(val listener:OnItemClickListener): RecyclerView.Adapter<FilmsHistoryAdapter.FilmsHistoryHolder>() {

    private var filmData:List<FilmInfoDTO> = listOf()

    fun setWeather(data:List<FilmInfoDTO>){
        this.filmData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmsHistoryHolder {
        return FilmsHistoryHolder(LayoutInflater.from(parent.context).inflate(R.layout.fragment_main_recycler_film_item,parent,false))
    }

    override fun onBindViewHolder(holder: FilmsHistoryHolder, position: Int) {
        holder.bind(this.filmData[position])
    }

    override fun getItemCount(): Int {
        return filmData.size
    }

    inner class FilmsHistoryHolder(view: View): RecyclerView.ViewHolder(view){
        fun bind(filmInfoDTO: FilmInfoDTO){
            FragmentMainRecyclerFilmItemBinding.bind(itemView).run {
                poster.load("$IMAGE_URL${filmInfoDTO.poster}")
                root.setOnClickListener{ listener.onItemClick(filmInfoDTO) }
            }
        }
    }
}