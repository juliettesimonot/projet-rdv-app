package com.example.rdv_app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.*
import com.example.rdv_app.databinding.DateButtonBinding
import com.example.rdv_app.databinding.DateFilmBinding
import com.example.rdv_app.model.DateBean
import com.example.rdv_app.model.ShowTimeBean

class DateFilmAdapter : ListAdapter<ShowTimeBean, DateFilmAdapter.ViewHolder>(
    DateFilmAdapter.Comparator()){


    class ViewHolder(var binding : DateFilmBinding) : RecyclerView.ViewHolder(binding.root)

    class Comparator : DiffUtil.ItemCallback<ShowTimeBean>() {
        override fun areItemsTheSame(oldItem: ShowTimeBean, newItem: ShowTimeBean): Boolean =
            oldItem === newItem


        override fun areContentsTheSame(oldItem: ShowTimeBean, newItem: ShowTimeBean): Boolean =
            false
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(DateFilmBinding.inflate(LayoutInflater.from(parent.context)))



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.binding.tvDay.text = currentItem.show_time_date_hour.split('-')[2].slice(0..1)
        holder.binding.tvMonth.text = currentItem.show_time_date_hour.split('-')[1]
        holder.binding.tvHour1.text = currentItem.show_time_date_hour.split('T')[1].slice(0..4)
    }
}