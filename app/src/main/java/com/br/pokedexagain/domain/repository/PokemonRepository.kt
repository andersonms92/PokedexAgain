package com.br.pokedexagain.domain.repository

import com.br.pokedexagain.domain.model.PokemonDetail
import com.br.pokedexagain.domain.model.PokemonListItem
import com.br.pokedexagain.domain.model.PokemonLocationEncounter
import com.br.pokedexagain.domain.model.PokemonSpeciesInfo

interface PokemonRepository {
    suspend fun getPokemonList(limit: Int = 100, offset: Int = 0): Result<List<PokemonListItem>>
    suspend fun getPokemonDetail(idOrName: String): Result<PokemonDetail>
    suspend fun getPokemonSpecies(idOrName: String): Result<PokemonSpeciesInfo>
    suspend fun getPokemonLocationEncounters(idOrName: String): Result<List<PokemonLocationEncounter>>
    suspend fun searchPokemon(query: String, typeFilter: String? = null): Result<List<PokemonListItem>>
}
