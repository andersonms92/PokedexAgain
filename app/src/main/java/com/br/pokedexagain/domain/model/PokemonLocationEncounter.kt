package com.br.pokedexagain.domain.model

data class PokemonLocationEncounter(
    val locationAreaName: String,
    val formattedLocationName: String,
    val versions: List<String>
)
