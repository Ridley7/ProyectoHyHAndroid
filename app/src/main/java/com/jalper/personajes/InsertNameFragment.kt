package com.jalper.personajes

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.jalper.personajes.databinding.FragmentInsertNameBinding
import com.jalper.personajes.model.PreferenceKeys
import org.koin.androidx.viewmodel.ext.android.getActivityViewModel


class InsertNameFragment : Fragment() {

    private val binding: FragmentInsertNameBinding by lazy{
        FragmentInsertNameBinding.inflate(layoutInflater)
    }

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
        binding.btnAddName?.setOnClickListener{

            //Comprobamos que se ha escrito algo en el edit text
            val playerName = binding.etPlayerName.text.toString()

            if(playerName.isNotEmpty()){

                preferences = this.getActivity()?.getSharedPreferences(PreferenceKeys.PREF_KEY, Context.MODE_PRIVATE)
                val editor: SharedPreferences.Editor? = preferences?.edit()
                editor?.putString(PreferenceKeys.NAME_KEY, playerName)
                editor?.apply()

                findNavController().navigate(R.id.action_insertNameFragment_to_mainMenuFragment)
            }


        }
    }

}