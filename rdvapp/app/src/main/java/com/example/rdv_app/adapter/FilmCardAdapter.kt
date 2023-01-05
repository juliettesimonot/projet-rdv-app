package com.example.rdv_app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rdv_app.R
import com.example.rdv_app.databinding.FilmCardBinding
import com.example.rdv_app.model.DateBean
import com.example.rdv_app.model.FilmBean
import com.squareup.picasso.Picasso

class FilmCardAdapter : ListAdapter<FilmBean, FilmCardAdapter.ViewHolder>(Comparator()) {

    var onFilmClick : ((FilmBean)->Unit)?=null

    class ViewHolder(var binding : FilmCardBinding) : RecyclerView.ViewHolder(binding.root)

    class Comparator : DiffUtil.ItemCallback<FilmBean>() {
        override fun areItemsTheSame(oldItem: FilmBean, newItem: FilmBean): Boolean =
            oldItem === newItem


        override fun areContentsTheSame(oldItem: FilmBean, newItem: FilmBean): Boolean =
            false
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(FilmCardBinding.inflate(LayoutInflater.from(parent.context)))



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentItem = getItem(position)
        var filmGenres = ""
        currentItem.film_genre.forEach {
            filmGenres+= "$it- "
        }

        var filmDirectors = ""
        currentItem.film_directors.forEach {
            filmDirectors+="$it "
        }

        var actors = ""
        currentItem.film_actors.forEach {
            actors+="$it- "
        }

        holder.binding.tvTitle.text = currentItem.film_name
        holder.binding.tvRuntime.text = currentItem.film_runtime
        holder.binding.tvRealeasedDate.text = currentItem.film_released_date?.split('-')?.get(0)
        holder.binding.tvPlot.text = currentItem.film_description?.split('.')?.get(0) ?:""
        holder.binding.tvGenre.text = filmGenres
        holder.binding.tvFilmDirector.text ="De : "+ filmDirectors
        holder.binding.tvActors.text = "Avec : "+actors
        Picasso.get().load(currentItem.film_img).into(holder.binding.ivCardImg)


        holder.binding.btSeeMovie.setOnClickListener(View.OnClickListener {
            onFilmClick?.invoke(currentItem)
//            Toast.makeText(holder.binding.tvMonth.context, "Voici la position : "+position, Toast.LENGTH_SHORT).show()
        })


    }
}