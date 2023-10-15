package com.jalper.personajes

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.jalper.personajes.databinding.FragmentMainMenuBinding
import com.jalper.personajes.model.PreferenceKeys
import com.jalper.personajes.model.ResourceState
import com.jalper.personajes.model.viewmodel.CharacterListState
import com.jalper.personajes.model.viewmodel.CharactersViewModel

class MainMenuFragment : Fragment() {

    private val binding: FragmentMainMenuBinding by lazy{
        FragmentMainMenuBinding.inflate(layoutInflater)
    }

    private var user_name: String = ""

    private var preferences: SharedPreferences? = null

    private lateinit var charactersViewModel: CharactersViewModel

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

        charactersViewModel.getCharacterLiveData().observe(viewLifecycleOwner){state ->
            handleCharacterListState(state)
        }
    }

    private fun handleCharacterListState(state: CharacterListState){
        when(state){
            is ResourceState.Loading ->{
                binding.pbMainMenu.visibility = View.VISIBLE
                binding.btnMainMenuJugar.visibility = View.GONE
                binding.btnMainMenuLista.visibility = View.GONE
                binding.btnHistorico.visibility = View.GONE
            }

            is ResourceState.Success ->{
                binding.pbMainMenu.visibility = View.GONE
                binding.btnMainMenuJugar.visibility = View.VISIBLE
                binding.btnMainMenuLista.visibility = View.VISIBLE
                binding.btnHistorico.visibility = View.VISIBLE

                //Aqui tenemos que recibir el resultado de la consulta a la API
                // y probablemente sacar las preguntas
            }

            is ResourceState.Error ->{
                binding.pbMainMenu.visibility = View.GONE
                binding.btnMainMenuJugar.visibility = View.VISIBLE
                binding.btnMainMenuLista.visibility = View.VISIBLE
                binding.btnHistorico.visibility = View.VISIBLE
            }
        }
    }

    private fun initUI(){
        //Seteamos nombre en la parte superior
        preferences = this.getActivity()?.getSharedPreferences(PreferenceKeys.PREF_KEY, Context.MODE_PRIVATE)
        user_name = preferences?.getString(PreferenceKeys.NAME_KEY, "mal")?: "null"
        binding.tvNombrePlayer.text = user_name

        //Seteamos navegacion en botones
        binding.btnMainMenuJugar.setOnClickListener{
            findNavController().navigate(R.id.action_mainMenuFragment_to_gameFragment)
        }

        binding.btnMainMenuLista.setOnClickListener{
            findNavController().navigate(R.id.action_mainMenuFragment_to_listaPersonajesFragment)
        }

        binding.btnHistorico.setOnClickListener{
            findNavController().navigate(R.id.action_mainMenuFragment_to_historicoPartidasFragment)
        }
    }

}