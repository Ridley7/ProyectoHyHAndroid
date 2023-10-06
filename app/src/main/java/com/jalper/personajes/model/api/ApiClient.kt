package com.jalper.personajes.model.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    /*
    val retrofit = Retrofit.Builder()
        .baseUrl("https://rickandmortyapi.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    */


    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.json-generator.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()!!


}