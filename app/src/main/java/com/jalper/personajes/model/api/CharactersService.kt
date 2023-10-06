package com.jalper.personajes.model.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface CharactersService {

    suspend fun getCharacters(): Response<CharactersResponse>
    @GET
    suspend fun getCharacters(@Url url: String) : Response<CharactersResponse>
}