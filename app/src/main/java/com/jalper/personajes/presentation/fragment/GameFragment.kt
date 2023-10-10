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

        //binding.ivCharacterProfile
        binding.btnOptionaCharacter.text = newCharacter.opcionA
        binding.btnOptionbCharacter.text = newCharacter.opcionB
        binding.btnOptioncCharacter.text = newCharacter.opcionC
        binding.btnOptiondCharacter.text = newCharacter.opcionD

        Glide.with(binding.ivCharacterProfile)
            .load(newCharacter.urlImage)
            .into(binding.ivCharacterProfile)

        //Asignamos la respuesta correcta al boton correspondiente
        when(newCharacter.opcionCorrecta){
            "opcion_a" -> {

                //Si la opcion A es la correcta, de momento, tenemos que navegar al success fragment
                //y los otros botones han de navegar al wrong fragment
                binding.btnOptionaCharacter.setOnClickListener{
                    findNavController().navigate(R.id.action_gameFragment_to_successFragment)
                }

                binding.btnOptionbCharacter.setOnClickListener{
                    findNavController().navigate(R.id.action_gameFragment_to_wrongFragment)
                }

                binding.btnOptioncCharacter.setOnClickListener{
                    findNavController().navigate(R.id.action_gameFragment_to_wrongFragment)
                }

                binding.btnOptiondCharacter.setOnClickListener{
                    findNavController().navigate(R.id.action_gameFragment_to_wrongFragment)
                }

            }

            "opcion_b" -> {

                binding.btnOptionbCharacter.setOnClickListener{
                    findNavController().navigate(R.id.action_gameFragment_to_successFragment)
                }

                binding.btnOptionaCharacter.setOnClickListener{
                    findNavController().navigate(R.id.action_gameFragment_to_wrongFragment)
                }

                binding.btnOptioncCharacter.setOnClickListener{
                    findNavController().navigate(R.id.action_gameFragment_to_wrongFragment)
                }

                binding.btnOptiondCharacter.setOnClickListener{
                    findNavController().navigate(R.id.action_gameFragment_to_wrongFragment)
                }

            }

            "opcion_c" -> {

                binding.btnOptioncCharacter.setOnClickListener{
                    findNavController().navigate(R.id.action_gameFragment_to_successFragment)
                }

                binding.btnOptionbCharacter.setOnClickListener{
                    findNavController().navigate(R.id.action_gameFragment_to_wrongFragment)
                }

                binding.btnOptionaCharacter.setOnClickListener{
                    findNavController().navigate(R.id.action_gameFragment_to_wrongFragment)
                }

                binding.btnOptiondCharacter.setOnClickListener{
                    findNavController().navigate(R.id.action_gameFragment_to_wrongFragment)
                }

            }

            "opcion_d" -> {

                binding.btnOptiondCharacter.setOnClickListener{
                    findNavController().navigate(R.id.action_gameFragment_to_successFragment)
                }

                binding.btnOptionbCharacter.setOnClickListener{
                    findNavController().navigate(R.id.action_gameFragment_to_wrongFragment)
                }

                binding.btnOptioncCharacter.setOnClickListener{
                    findNavController().navigate(R.id.action_gameFragment_to_wrongFragment)
                }

                binding.btnOptionaCharacter.setOnClickListener{
                    findNavController().navigate(R.id.action_gameFragment_to_wrongFragment)
                }

            }
            else -> Log.i("ERROR", "AQUI LA HAS LIADO")
        }

    }




}

