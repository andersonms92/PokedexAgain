package com.br.pokedexagain.ui.pokedex

import com.br.pokedexagain.domain.model.PokemonDetail
import com.br.pokedexagain.domain.model.PokemonLocationEncounter
import com.br.pokedexagain.domain.model.PokemonSpeciesInfo

sealed interface PokemonDetailUiState {
    data object Loading : PokemonDetailUiState
    data class Success(
        val detail: PokemonDetail,
        val species: PokemonSpeciesInfo?,
        val locations: List<PokemonLocationEncounter>
    ) : PokemonDetailUiState
    data class Error(val message: String) : PokemonDetailUiState
}
