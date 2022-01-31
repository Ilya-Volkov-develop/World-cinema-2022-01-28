package ru.iliavolkov.worldcinema.view.main.mainscreen

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.iliavolkov.worldcinema.R
import ru.iliavolkov.worldcinema.databinding.FragmentMainRecyclerFilmItemBinding
import ru.iliavolkov.worldcinema.model.FilmInfoDTO
import ru.iliavolkov.worldcinema.utils.IMAGE_URL

class MainFragmentAdapter(val listener: OnItemClickListener): RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {

    private var filmData:List<FilmInfoDTO> = listOf()

    fun setFilm(data: List<FilmInfoDTO>){
        this.filmData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.fragment_main_recycler_film_item,parent,false))
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(this.filmData[position])
    }

    override fun getItemCount(): Int {
        return filmData.size
    }

    inner class MainViewHolder(view: View):RecyclerView.ViewHolder(view){
        fun bind(film: FilmInfoDTO){
            FragmentMainRecyclerFilmItemBinding.bind(itemView).run {
                poster.load("$IMAGE_URL${film.poster}")
                recyclerItemName.text = film.name
                root.setOnClickListener{ listener.onItemClick(film) }
            }


        }
    }
}