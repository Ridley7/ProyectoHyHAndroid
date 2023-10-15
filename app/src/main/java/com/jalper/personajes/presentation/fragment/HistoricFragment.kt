package com.jalper.personajes.presentation.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jalper.personajes.R
import com.jalper.personajes.databinding.FragmentHistoricBinding
import com.jalper.personajes.model.Game
import com.jalper.personajes.model.PreferenceKeys
import com.jalper.personajes.presentation.adapter.HistoricListAdapter

class HistoricFragment : Fragment() {

    private val binding: FragmentHistoricBinding by lazy{
        FragmentHistoricBinding.inflate(layoutInflater)
    }

    private var preferences: SharedPreferences? = null
    private var historic: String = ""
    private val historicListAdapter = HistoricListAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadHistoricGames()
        initUI()
    }

    private fun initUI(){
        binding.rvHistoricList.adapter = historicListAdapter
        binding.rvHistoricList.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun loadHistoricGames(){

        preferences = this.getActivity()?.getSharedPreferences(PreferenceKeys.PREF_KEY, Context.MODE_PRIVATE)
        historic = preferences?.getString(PreferenceKeys.HISTORIC_KEY, "null") ?: "null"

        if(historic != "null"){

            //Si no es null extramos la lista de games
            val gson = Gson()

            val type = object : TypeToken<MutableList<Game>>(){}.type
            var listaGame : MutableList<Game> = mutableListOf<Game>()
            listaGame = gson.fromJson(historic, type)

            historicListAdapter.submitList(listaGame)

        }
    }

}