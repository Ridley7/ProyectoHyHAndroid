package com.jalper.personajes.presentation.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.jalper.personajes.databinding.FragmentListaPersonajesBinding
import com.jalper.personajes.model.ResourceState
import com.jalper.personajes.model.viewmodel.CharacterListState
import com.jalper.personajes.model.viewmodel.CharactersViewModel
import com.jalper.personajes.presentation.adapter.CharactersListAdapter

class ListaPersonajesFragment : Fragment() {

    private val binding: FragmentListaPersonajesBinding by lazy {
        FragmentListaPersonajesBinding.inflate(layoutInflater)
    }

    private val charactersListAdapter = CharactersListAdapter()
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

        charactersViewModel.fetchCharacters()
    }

    private fun initUI(){
        binding.rvCharacterList.adapter = charactersListAdapter
        binding.rvCharacterList.layoutManager = LinearLayoutManager(requireContext())
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
                binding.pbCharacterList.visibility = View.VISIBLE
            }
            is ResourceState.Success ->{
                binding.pbCharacterList.visibility = View.GONE
                charactersListAdapter.submitList(state.result)
            }

            is ResourceState.Error ->{
                binding.pbCharacterList.visibility = View.GONE
                Toast.makeText(requireContext(), state.error, Toast.LENGTH_SHORT).show()

            }
        }
    }
}