package com.jalper.personajes.model.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jalper.personajes.model.ResourceState
import com.jalper.personajes.model.api.ApiClient
import com.jalper.personajes.model.api.CharacterResponseElement
import com.jalper.personajes.model.api.CharactersService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

typealias CharacterListState = ResourceState<List<CharacterResponseElement>>

class CharactersViewModel: ViewModel() {
    private val charactersService = ApiClient.retrofit.create(CharactersService::class.java)

    private val characterMutableLiveData = MutableLiveData<CharacterListState>()
    var charactersForGame: List<CharacterResponseElement> = emptyList()
    private var puntuation = 0

    fun getCharacterLiveData(): LiveData<CharacterListState> {
        return  characterMutableLiveData
    }

    fun setPuntuation(value: Int){
        puntuation = value
    }

    fun getPuntuation():Int{
        return puntuation
    }

    fun addPuntuation(value: Int){
        puntuation += value
    }

    fun fetchCharacters(){
        characterMutableLiveData.value = ResourceState.Loading()

        viewModelScope.launch (Dispatchers.IO) {
            val response = charactersService.getCharacters("templates/hmq7USqKZCQ_/data?access_token=l5wzz9nldojijjatx9h6klgykv5ztvi4jxdsndgp")

            //Nos vamos al hilo principal!!!
            withContext(Dispatchers.Main){
                try {
                    if(response.isSuccessful && response.body() != null){
                        characterMutableLiveData.value = ResourceState.Success(response.body()!!.personajes)
                    }else{
                        characterMutableLiveData.value = ResourceState.Error(response.errorBody()?.string().orEmpty())
                    }
                }catch (e: Exception){
                    characterMutableLiveData.value = ResourceState.Error("Ha ocurrido un error")
                }
            }
        }
    }

    fun getRandomCharacter(): CharacterResponseElement{
        val random = java.util.Random()
        val randomIndex = random.nextInt(charactersForGame.size)
        return charactersForGame[randomIndex]
    }

    fun setupCharactersForGame(): LiveData<CharacterListState>{
        characterMutableLiveData.value = ResourceState.Loading()

        viewModelScope.launch (Dispatchers.IO){
            //Obtenemos personajes
            val response = charactersService.getCharacters("templates/hmq7USqKZCQ_/data?access_token=l5wzz9nldojijjatx9h6klgykv5ztvi4jxdsndgp")

            //Nos vamos al hilo principal
            withContext(Dispatchers.Main){
                try {
                    if(response.isSuccessful && response.body() != null){
                        characterMutableLiveData.value = ResourceState.Success(response.body()!!.personajes)
                        charactersForGame = response.body()!!.personajes

                    }else{
                        characterMutableLiveData.value = ResourceState.Error(response.errorBody()?.string().orEmpty())
                    }
                }catch (e: Exception){
                    characterMutableLiveData.value = ResourceState.Error("Ha ocurrido un error")
                }
            }
        }

        return characterMutableLiveData
    }


}