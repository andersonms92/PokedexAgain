package com.br.pokedexagain.ui.pokedex

import com.br.pokedexagain.domain.model.PokemonListItem

sealed interface PokedexUiState {
    data object Loading : PokedexUiState
    data class Success(
        val pokemonList: List<PokemonListItem>
    ) : PokedexUiState
    data class Error(val message: String) : PokedexUiState
}
