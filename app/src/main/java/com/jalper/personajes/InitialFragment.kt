package com.jalper.personajes

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.jalper.personajes.databinding.FragmentInitialBinding
import com.jalper.personajes.databinding.FragmentInsertNameBinding
import com.jalper.personajes.model.PreferenceKeys

class InitialFragment : Fragment() {

    private var preferences: SharedPreferences? = null
    private var user_name: String = ""

    private val binding: FragmentInitialBinding by lazy{
        FragmentInitialBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_initial, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        //Comprobamos las shared preferences
        preferences = this.getActivity()?.getSharedPreferences(PreferenceKeys.PREF_KEY, Context.MODE_PRIVATE)

        user_name = preferences?.getString(PreferenceKeys.NAME_KEY, "null") ?: "abc"

        //Si obtenemos null no tenemos nada en shared preferences
        if(user_name == "null"){
            //Cargamos la pantalla de pedir nombre
            findNavController().navigate(R.id.action_initialFragment_to_insertNameFragment)
        }else{
            //Cargamos menu principal
            findNavController().navigate(R.id.action_initialFragment_to_mainMenuFragment)
        }

    }


}