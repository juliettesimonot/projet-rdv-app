package com.example.rdv_app.adapter

import android.graphics.ColorSpace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rdv_app.databinding.DateButtonBinding
import com.example.rdv_app.model.DateBean

class CalendarDateAdapter : ListAdapter<String, CalendarDateAdapter.ViewHolder>(
    CalendarDateAdapter.Comparator()){
    var onDateClick : ((String)->Unit)?=null

    class ViewHolder(var binding : DateButtonBinding) : RecyclerView.ViewHolder(binding.root)


    class Comparator : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem === newItem


        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
            false
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(DateButtonBinding.inflate(LayoutInflater.from(parent.context)))



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.binding.tvDay.text = currentItem.split('-')[2]
        holder.binding.tvMonth.text = currentItem.split('-')[1]

        holder.binding.tvDate.setOnClickListener(View.OnClickListener {
            onDateClick?.invoke(currentItem)
        })

    }
}