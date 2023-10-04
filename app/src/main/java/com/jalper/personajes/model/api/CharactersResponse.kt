package com.jalper.personajes.model.api

typealias CharacterResponse = ArrayList<CharacterResponseElement>

data class CharacterResponseElement (
    val opcionA: String,
    val opcionB: String,
    val opcionC: String,
    val opcionD: String,
    val urlImage: String,
    val descripcion: String,
    val opcionCorrecta: String,
    val nombrePersonaje: String
)

/*
class CharactersResponse {
}

 */