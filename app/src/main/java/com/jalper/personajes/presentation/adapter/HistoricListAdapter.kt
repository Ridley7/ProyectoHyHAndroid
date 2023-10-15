package com.jalper.personajes.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jalper.personajes.databinding.RowHistoricItemBinding
import com.jalper.personajes.model.Game
import java.util.Date

class HistoricListAdapter : RecyclerView.Adapter<HistoricListAdapter.HistoricListViewHolder>(){

    private var historicList : List<Game> = emptyList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoricListViewHolder {
        val binding = RowHistoricItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HistoricListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return historicList.size
    }

    override fun onBindViewHolder(holder: HistoricListViewHolder, position: Int) {
        val item = historicList[position]

        holder.itemName.text = item.player
        val date = Date(item.date)
        holder.itemDate.text = date.toString()
        holder.itemPoints.text = item.puntuation.toString()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<Game>) {
        historicList = list
        notifyDataSetChanged()
    }

    inner class HistoricListViewHolder(binding: RowHistoricItemBinding): RecyclerView.ViewHolder(binding.root){
        val itemName = binding.tvHistoricName
        val itemDate = binding.tvHistoricDate
        val itemPoints = binding.tvHistoricPoints
    }
}