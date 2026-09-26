package com.br.pokedexagain.domain.model

data class PokemonListItem(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val types: List<String> = emptyList(),
    val generation: Int = 1,
    val generationName: String = "Gen 1 (Kanto)"
)
