package com.jalper.personajes.presentation.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.jalper.personajes.R
import com.jalper.personajes.databinding.FragmentGameBinding
import com.jalper.personajes.model.ResourceState
import com.jalper.personajes.model.api.ApiClient
import com.jalper.personajes.model.viewmodel.CharacterListState
import com.jalper.personajes.model.viewmodel.CharactersViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GameFragment : Fragment(){

    private val binding: FragmentGameBinding by lazy {
        FragmentGameBinding.inflate(layoutInflater)
    }

    private lateinit var charactersViewModel: CharactersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()
    }

    private fun initViewModel(){

        charactersViewModel = ViewModelProvider(requireActivity()).get(CharactersViewModel::class.java)
        charactersViewModel.setupCharactersForGame().observe(viewLifecycleOwner){state ->
            handleCharacterListState(state)
        }
    }

    private fun handleCharacterListState(state: CharacterListState){
        when(state){
            is ResourceState.Loading ->{
                binding.pbGame.visibility = View.VISIBLE
            }
            is ResourceState.Success ->{
                binding.pbGame.visibility = View.GONE
                charactersViewModel.charactersForGame = state.result
                initUI()
            }

            is ResourceState.Error ->{
                binding.pbGame.visibility = View.GONE
                Toast.makeText(requireContext(), state.error, Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun initUI(){

        //Seteamos textos en los botones y imagen del personaje
        val newCharacter = charactersViewModel.getRandomCharacter()

        binding.btnOptionaCharacter.text = newCharacter.opcionA
        binding.btnOptionbCharacter.text = newCharacter.opcionB
        binding.btnOptioncCharacter.text = newCharacter.opcionC
        binding.btnOptiondCharacter.text = newCharacter.opcionD

        Glide.with(binding.ivCharacterProfile)
            .load(newCharacter.urlImage)
            .into(binding.ivCharacterProfile)

        //Seteamos listener en los botones del fragmento de juego
        val correctOption = newCharacter.opcionCorrecta

        val onCorrectAnswerClick = {
            findNavController().navigate(R.id.action_gameFragment_to_successFragment)
        }

        val onWrongAnswerClick = { wrongAnser: String ->
            findNavController().navigate(
                GameFragmentDirections.actionGameFragmentToWrongFragment(
                    wrongAnswer = wrongAnser
                )
            )
        }

        binding.btnOptionaCharacter.setOnClickListener {
            if (correctOption == "opcion_a") {
                onCorrectAnswerClick()
            } else {
                onWrongAnswerClick(newCharacter.opcionA)
            }
        }

        binding.btnOptionbCharacter.setOnClickListener {
            if (correctOption == "opcion_b") {
                onCorrectAnswerClick()
            } else {
                onWrongAnswerClick(newCharacter.opcionB)
            }
        }

        binding.btnOptioncCharacter.setOnClickListener {
            if (correctOption == "opcion_c") {
                onCorrectAnswerClick()
            } else {
                onWrongAnswerClick(newCharacter.opcionC)
            }
        }

        binding.btnOptiondCharacter.setOnClickListener {
            if (correctOption == "opcion_d") {
                onCorrectAnswerClick()
            } else {
                onWrongAnswerClick(newCharacter.opcionD)
            }
        }
    }

}

