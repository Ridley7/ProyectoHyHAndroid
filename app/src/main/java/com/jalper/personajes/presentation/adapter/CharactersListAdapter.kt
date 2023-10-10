package com.jalper.personajes.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jalper.personajes.databinding.RowCharacterItemBinding
import com.jalper.personajes.model.api.CharacterResponseElement

class CharactersListAdapter: RecyclerView.Adapter<CharactersListAdapter.CharactersListViewHolder>() {

    private var characterList: List<CharacterResponseElement> = emptyList()
    //private var characterList: List<Character> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersListViewHolder {
        val binding = RowCharacterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharactersListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return characterList.size
    }

    override fun onBindViewHolder(holder: CharactersListViewHolder, position: Int) {
        val item = characterList[position]

        holder.itemCharacterName.text = item.nombrePersonaje


        Glide.with(holder.itemCharacterProfile)
            .load(item.urlImage)
            .into(holder.itemCharacterProfile)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<CharacterResponseElement>) {
        characterList = list
        notifyDataSetChanged()
    }

    inner class CharactersListViewHolder(binding: RowCharacterItemBinding) : RecyclerView.ViewHolder(binding.root){
        val rootView = binding.root
        val itemCharacterName = binding.tvItemCharacterName
        val itemCharacterProfile = binding.ivItemCharacterProfile
    }

}