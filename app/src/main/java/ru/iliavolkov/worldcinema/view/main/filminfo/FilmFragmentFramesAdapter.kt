package ru.iliavolkov.worldcinema.view.main.filminfo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import ru.iliavolkov.worldcinema.R
import ru.iliavolkov.worldcinema.databinding.FragmentFilmFramesRecyclerItemBinding
import ru.iliavolkov.worldcinema.utils.IMAGE_URL

class FilmFragmentFramesAdapter(val listener: OnItemClickListener): RecyclerView.Adapter<FilmFragmentFramesAdapter.FilmFramesViewHolder>() {

    private var framesData:List<String> = listOf()

    fun setFrames(data: List<String>){
        this.framesData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmFramesViewHolder {
        return FilmFramesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.fragment_film_frames_recycler_item,parent,false))
    }

    override fun onBindViewHolder(holder: FilmFramesViewHolder, position: Int) {
        holder.bind(this.framesData[position])
    }

    override fun getItemCount(): Int {
        return framesData.size
    }

    inner class FilmFramesViewHolder(view: View):RecyclerView.ViewHolder(view){
        fun bind(frames: String){
            FragmentFilmFramesRecyclerItemBinding.bind(itemView).run {
                framesImage.load("$IMAGE_URL${frames}")
                root.setOnClickListener{ listener.onItemClick() }
            }


        }
    }
}