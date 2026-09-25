package com.br.pokedexagain.domain.usecase

import com.br.pokedexagain.domain.model.PokemonStats
import com.br.pokedexagain.domain.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonStatsUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(idOrName: String): Result<PokemonStats> {
        return repository.getPokemonDetail(idOrName).map { it.stats }
    }
}
