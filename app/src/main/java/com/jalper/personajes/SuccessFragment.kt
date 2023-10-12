package com.jalper.personajes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.jalper.personajes.databinding.FragmentSuccessBinding
import com.jalper.personajes.model.viewmodel.CharactersViewModel


class SuccessFragment : Fragment() {

    private val binding: FragmentSuccessBinding by lazy{
        FragmentSuccessBinding.inflate(layoutInflater)
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

    private fun initUI(){
        binding.btnNextQuestion.setOnClickListener{
            findNavController().navigate(R.id.action_successFragment_to_gameFragment)
        }

        binding.tvPoints.text = charactersViewModel.getPuntuation().toString()
    }

    private fun initViewModel(){
        charactersViewModel = ViewModelProvider(requireActivity()).get(CharactersViewModel::class.java)

        //Añadimos puntuacion
        charactersViewModel.setPuntuation(10)
    }




}