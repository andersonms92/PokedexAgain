package com.br.pokedexagain.domain.usecase

import com.br.pokedexagain.domain.model.PokemonListItem
import com.br.pokedexagain.domain.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonListUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(limit: Int = 100, offset: Int = 0): Result<List<PokemonListItem>> {
        return repository.getPokemonList(limit, offset)
    }
}
