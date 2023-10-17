package com.jalper.personajes.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jalper.personajes.databinding.RowHistoricItemBinding
import com.jalper.personajes.model.Game
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
        val pattern = "dd/MM/yyyy HH:mm"

        holder.itemName.text = item.player
        val date = Date(item.date)
        holder.itemDate.text = formatDate(date)
        holder.itemPoints.text = "${item.puntuation} puntos."
    }

    fun formatDate(date: Date): String {
        val pattern = "dd/MM/yyyy HH:mm"
        val sdf = SimpleDateFormat(pattern, Locale.getDefault())
        return sdf.format(date)
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