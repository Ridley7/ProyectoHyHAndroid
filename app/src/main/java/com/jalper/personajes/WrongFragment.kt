package com.jalper.personajes

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
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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
        //inicializamos view model
        charactersViewModel = ViewModelProvider(requireActivity()).get(CharactersViewModel::class.java)

        //guardamos resultados en algun lado por ejemplo player prefs
        preferences = this.getActivity()?.getSharedPreferences(PreferenceKeys.PREF_KEY, Context.MODE_PRIVATE)

        historic = preferences?.getString(PreferenceKeys.HISTORIC_KEY, "null") ?: "null"

        userName = (preferences?.getString(PreferenceKeys.NAME_KEY, "null") ?: null).toString()

        if(historic == "null"){

            //Creamos JSON
            //1. Creamos un objeto de la clase Game
            val game = Game(
                player = userName,
                puntuation = charactersViewModel.getPuntuation(),
                date = System.currentTimeMillis()
            )

            //2. Creamos una lista de game
            val listaPartidas = mutableListOf<Game>()

            //3. Metemos el juego en la lista
            listaPartidas.add(game)

            //4. Convertimos a JSON
            val gson = Gson()
            val json = gson.toJson(listaPartidas)

            //5. Metemos el json en los shared preferences
            val editor: SharedPreferences.Editor? = preferences?.edit()
            editor?.putString(PreferenceKeys.HISTORIC_KEY, json)
            editor?.apply()

        }
        else{
            //Si no es null, extraemos la lista de games
            val gson = Gson()

            val type = object : TypeToken<MutableList<Game>>(){}.type
            var listaGame : MutableList<Game> = mutableListOf<Game>()
            listaGame = gson.fromJson(historic, type)

            //Comprobamos el tamaño de la lista
            if(listaGame.size >= 10){
                //Eliminamos el ultimo elemento de la lista
                listaGame.removeLast()

                //Desplazamos todos los elementos de la lista
                val lastIndex = listaGame.size - 1

                for(i in lastIndex downTo 1){
                    listaGame[i] = listaGame[i - 1]
                }

                //Insertamos en la primera posicion el elemento nuevo
                val game = Game(
                    player = userName,
                    puntuation = charactersViewModel.getPuntuation(),
                    date = System.currentTimeMillis()
                )

                listaGame[0] = game

                //Guardamos la lista en Shared preferences
                val json = gson.toJson(listaGame)

                //Metemos el json en los shared preferences
                val editor: SharedPreferences.Editor? = preferences?.edit()
                editor?.putString(PreferenceKeys.HISTORIC_KEY, json)
                editor?.apply()

            }else{
                //Añadimos la nueva informacion
                //1. Creamos un objeto de la clase Game
                val game = Game(
                    player = userName,
                    puntuation = charactersViewModel.getPuntuation(),
                    date = System.currentTimeMillis()
                )

                //2. Metemos el juego en la lista
                listaGame.add(game)

                //3. Convertimos a json
                val json = gson.toJson(listaGame)
                Log.i("FLAG 200", json)

                //4. Metemos el json en los shared preferences
                val editor: SharedPreferences.Editor? = preferences?.edit()
                editor?.putString(PreferenceKeys.HISTORIC_KEY, json)
                editor?.apply()
            }
        }

        //ponemos puntuacion a 0
        charactersViewModel.setPuntuation(0)
    }

    private fun initUI(){
        //Seteamos botones de intentar otra vez y saliar
        binding.btnExitGame.setOnClickListener{
            findNavController().navigate(R.id.action_wrongFragment_to_mainMenuFragment)
        }

        binding.btnTryAgain.setOnClickListener{

            findNavController().navigate(R.id.action_wrongFragment_to_gameFragment)
        }
    }

}