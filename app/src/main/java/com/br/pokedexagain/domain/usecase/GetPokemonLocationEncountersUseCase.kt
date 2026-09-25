package com.br.pokedexagain.domain.usecase

import com.br.pokedexagain.domain.model.PokemonLocationEncounter
import com.br.pokedexagain.domain.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonLocationEncountersUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(idOrName: String): Result<List<PokemonLocationEncounter>> {
        return repository.getPokemonLocationEncounters(idOrName)
    }
}
