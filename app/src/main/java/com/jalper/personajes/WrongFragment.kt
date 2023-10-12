package com.jalper.personajes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.jalper.personajes.databinding.FragmentWrongBinding
import com.jalper.personajes.model.viewmodel.CharactersViewModel


class WrongFragment : Fragment() {

    private val binding: FragmentWrongBinding by lazy {
        FragmentWrongBinding.inflate(layoutInflater)
    }

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
        //inicializamos view model
        charactersViewModel = ViewModelProvider(requireActivity()).get(CharactersViewModel::class.java)

        //guardamos resultados en algun lado, por ejemplo player prefs

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