package com.jalper.personajes

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.jalper.personajes.databinding.FragmentMainMenuBinding
import com.jalper.personajes.model.PreferenceKeys

class MainMenuFragment : Fragment() {

    private val binding: FragmentMainMenuBinding by lazy{
        FragmentMainMenuBinding.inflate(layoutInflater)
    }

    private var user_name: String = ""

    private var preferences: SharedPreferences? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
    }


    private fun initUI(){
        //Seteamos nombre en la parte superior
        preferences = this.getActivity()?.getSharedPreferences(PreferenceKeys.PREF_KEY, Context.MODE_PRIVATE)
        user_name = preferences?.getString(PreferenceKeys.NAME_KEY, "mal")?: "null"
        binding.tvNombrePlayer.text = user_name

        //Seteamos navegacion en botones
        binding.btnMainMenuLista.setOnClickListener{
            findNavController().navigate(R.id.action_mainMenuFragment_to_listaPersonajesFragment)
        }
    }

}