package com.br.pokedexagain.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GenerationDto(
    @param:Json(name = "id") val id: Int,
    @param:Json(name = "name") val name: String,
    @param:Json(name = "pokemon_species") val pokemonSpecies: List<NamedApiResourceDto>
)
