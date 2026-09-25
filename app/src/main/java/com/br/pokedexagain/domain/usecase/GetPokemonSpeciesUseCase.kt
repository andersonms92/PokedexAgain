package com.br.pokedexagain.domain.usecase

import com.br.pokedexagain.domain.model.PokemonSpeciesInfo
import com.br.pokedexagain.domain.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonSpeciesUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(idOrName: String): Result<PokemonSpeciesInfo> {
        return repository.getPokemonSpecies(idOrName)
    }
}
