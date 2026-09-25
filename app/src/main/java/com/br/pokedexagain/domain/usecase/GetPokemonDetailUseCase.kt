package com.br.pokedexagain.domain.usecase

import com.br.pokedexagain.domain.model.PokemonDetail
import com.br.pokedexagain.domain.repository.PokemonRepository
import javax.inject.Inject

class GetPokemonDetailUseCase @Inject constructor(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(idOrName: String): Result<PokemonDetail> {
        return repository.getPokemonDetail(idOrName)
    }
}
