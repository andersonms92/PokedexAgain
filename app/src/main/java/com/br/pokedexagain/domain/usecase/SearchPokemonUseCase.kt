package com.br.pokedexagain.domain.usecase

import com.br.pokedexagain.domain.model.PokemonListItem
import com.br.pokedexagain.domain.repository.PokemonRepository
import javax.inject.Inject

class SearchPokemonUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(query: String, typeFilter: String? = null): Result<List<PokemonListItem>> {
        return repository.searchPokemon(query, typeFilter)
    }
}
