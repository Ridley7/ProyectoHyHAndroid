package com.jalper.personajes.presentation.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jalper.personajes.R
import com.jalper.personajes.databinding.FragmentWrongBinding
import com.jalper.personajes.model.Game
import com.jalper.personajes.model.PreferenceKeys
import com.jalper.personajes.model.viewmodel.CharactersViewModel


class WrongFragment : Fragment() {

    private val binding: FragmentWrongBinding by lazy {
        FragmentWrongBinding.inflate(layoutInflater)
    }

    private lateinit var charactersViewModel: CharactersViewModel
    private var preferences: SharedPreferences? = null
    private var historic: String = ""
    private var userName: String = ""
    val args: WrongFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
        initUI()
    }

    private fun initViewModel(){
        charactersViewModel = ViewModelProvider(requireActivity()).get(CharactersViewModel::class.java)
        preferences = this.getActivity()?.getSharedPreferences(PreferenceKeys.PREF_KEY, Context.MODE_PRIVATE)

        historic = preferences?.getString(PreferenceKeys.HISTORIC_KEY, "null") ?: "null"
        userName = (preferences?.getString(PreferenceKeys.NAME_KEY, "null") ?: null).toString()

        val gson = Gson()
        var listaGame = mutableListOf<Game>()

        if(historic == "null"){
            listaGame.add(createGame(userName, charactersViewModel.getPuntuation()))
        }
        else
        {
            listaGame = gson.fromJson(historic, object : TypeToken<MutableList<Game>>() {}.type)

            //Comprobamos el tamaño de la lista
            if(listaGame.size >= 10){
                shiftElementsInList(listaGame)
                listaGame[0] = createGame(userName, charactersViewModel.getPuntuation())

            }else
            {
                listaGame.add(createGame(userName, charactersViewModel.getPuntuation()))
            }
        }
        saveGameListToJsonAndPreferences(listaGame)

        charactersViewModel.setPuntuation(0)
    }

    private fun createGame(player: String, puntuation: Int): Game {
        return Game(
            player = player,
            puntuation = puntuation,
            date = System.currentTimeMillis()
        )
    }

    private fun shiftElementsInList(listaGame: MutableList<Game>) {
        for (i in listaGame.size - 1 downTo 1) {
            listaGame[i] = listaGame[i - 1]
        }
    }

    private fun saveGameListToJsonAndPreferences(listaGame: MutableList<Game>) {
        val gson = Gson()
        val json = gson.toJson(listaGame)
        preferences?.edit()?.putString(PreferenceKeys.HISTORIC_KEY, json)?.apply()
    }

    private fun initUI(){
        //Seteamos botones de intentar otra vez y salir
        binding.btnExitGame.setOnClickListener{
            findNavController().navigate(R.id.action_wrongFragment_to_mainMenuFragment)
        }

        binding.btnTryAgain.setOnClickListener{
            findNavController().navigate(R.id.action_wrongFragment_to_gameFragment)
        }

        //Seteamos el text view de respuesta correcta
        binding.tvAnswer.text = args.correctAnswer
    }

}