package com.br.pokedexagain.domain.model

data class PokemonDetail(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val shinyImageUrl: String?,
    val heightInMeters: Double,
    val weightInKg: Double,
    val types: List<String>,
    val abilities: List<String>,
    val stats: PokemonStats
)
