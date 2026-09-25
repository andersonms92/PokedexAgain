package com.br.pokedexagain.domain.model

data class PokemonSpeciesInfo(
    val id: Int,
    val genus: String,
    val flavorText: String,
    val captureRate: Int?,
    val habitat: String?,
    val isLegendary: Boolean,
    val isMythical: Boolean
)
