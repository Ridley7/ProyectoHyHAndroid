package com.jalper.personajes.model.api
/*
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

data class CharactersResponse (
    @SerializedName("results") val characters: List<Character>
)

@Keep
data class Character (
    val id: Long,
    val name: String,
    val status: Status,
    val species: Species,
    val type: String,
    val gender: Gender,
    val origin: Location,
    val location: Location,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
)

@Keep
enum class Gender {
    Female,
    Male,
    Unknown
}

@Keep
data class Location (
    val name: String,
    val url: String
)

@Keep
enum class Species {
    Alien,
    Human
}

@Keep
enum class Status {
    Alive,
    Dead,
    Unknown
}
*/


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName


data class CharactersResponse(
    @SerializedName("hola") val personajes: List<CharacterResponseElement>
)

@Keep
data class CharacterResponseElement (
    @SerializedName("opcion_a") val opcionA: String,
    @SerializedName("opcion_b") val opcionB: String,
    @SerializedName("opcion_c") val opcionC: String,
    @SerializedName("opcion_d") val opcionD: String,
    @SerializedName("url_image") val urlImage: String,
    @SerializedName("descripcion") val descripcion: String,
    @SerializedName("opcion_correcta") val opcionCorrecta: String,
    @SerializedName("nombre_personaje") val nombrePersonaje: String
)
