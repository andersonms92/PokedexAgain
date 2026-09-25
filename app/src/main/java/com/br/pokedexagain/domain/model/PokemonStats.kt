package com.br.pokedexagain.domain.model

data class PokemonStats(
    val hp: Int = 0,
    val attack: Int = 0,
    val defense: Int = 0,
    val specialAttack: Int = 0,
    val specialDefense: Int = 0,
    val speed: Int = 0
) {
    val totalStats: Int get() = hp + attack + defense + specialAttack + specialDefense + speed
}
