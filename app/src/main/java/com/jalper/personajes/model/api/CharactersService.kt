package com.jalper.personajes.model.api

import retrofit2.Response
import retrofit2.http.GET

interface CharactersService {

    suspend fun getCharacters(): Response<CharactersResponse>
}