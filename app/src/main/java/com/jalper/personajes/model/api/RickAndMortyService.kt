package com.jalper.personajes.model.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import java.lang.Character

interface RickAndMortyService {

    @GET("character")
    suspend fun getCharacters(): Response<CharactersResponse>

    @GET("character/{characterId}")
    suspend fun getCharacter(@Path("") characterId: Int): Response<Character>
}