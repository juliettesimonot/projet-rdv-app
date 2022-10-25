package com.example.rdv_app.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rdv_app.R
import com.example.rdv_app.databinding.FilmCardBinding
import com.example.rdv_app.model.FilmBean
import com.squareup.picasso.Picasso

class FilmCardAdapter : ListAdapter<FilmBean, FilmCardAdapter.ViewHolder>(Comparator()) {

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

        holder.binding.tvTitle.text = currentItem.film_title
        holder.binding.tvRuntime.text = currentItem.film_runtime
        holder.binding.tvRealeasedDate.text = currentItem.film_released_date
        holder.binding.tvPlot.text = currentItem.film_description
        holder.binding.tvGenre.text = currentItem.film_genre
        Picasso.get().load(currentItem.film_img).into(holder.binding.ivCardImg)


    }
}