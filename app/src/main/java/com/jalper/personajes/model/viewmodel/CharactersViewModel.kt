package com.jalper.personajes.model.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jalper.personajes.model.ResourceState
import com.jalper.personajes.model.api.ApiClient
import com.jalper.personajes.model.api.Character
import com.jalper.personajes.model.api.RickAndMortyService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

typealias CharacterListState = ResourceState<List<Character>>

class CharactersViewModel: ViewModel() {
    //private val charactersService = ApiClient.retrofit.create(CharactersService::class.java)
    private val rickAndMortyService = ApiClient.retrofit.create(RickAndMortyService::class.java)

    private val characterMutableLiveData = MutableLiveData<CharacterListState>()

    fun getCharacterLiveData(): LiveData<CharacterListState> {
        return  characterMutableLiveData
    }

    fun fetchCharacters(){
        characterMutableLiveData.value = ResourceState.Loading()

        viewModelScope.launch (Dispatchers.IO) {
            val response = rickAndMortyService.getCharacters()

            //Nos vamos al hilo principal!!!
            withContext(Dispatchers.Main){
                try {
                    if(response.isSuccessful && response.body() != null){
                        characterMutableLiveData.value = ResourceState.Success(response.body()!!.characters)
                    }else{
                        characterMutableLiveData.value = ResourceState.Error(response.errorBody()?.string().orEmpty())
                    }
                }catch (e: Exception){
                    characterMutableLiveData.value = ResourceState.Error("Ha ocurrido un error")
                }
            }
        }
    }


}