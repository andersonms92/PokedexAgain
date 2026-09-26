package com.br.pokedexagain.data.remote.api

import com.br.pokedexagain.data.remote.dto.GenerationDto
import com.br.pokedexagain.data.remote.dto.PokemonDetailDto
import com.br.pokedexagain.data.remote.dto.PokemonListResponseDto
import com.br.pokedexagain.data.remote.dto.PokemonLocationEncounterDto
import com.br.pokedexagain.data.remote.dto.PokemonSpeciesDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApiService {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = 1302,
        @Query("offset") offset: Int = 0
    ): PokemonListResponseDto

    @GET("generation/{id}")
    suspend fun getGeneration(
        @Path("id") id: Int
    ): GenerationDto

    @GET("pokemon/{idOrName}")
    suspend fun getPokemonDetail(
        @Path("idOrName") idOrName: String
    ): PokemonDetailDto

    @GET("pokemon-species/{idOrName}")
    suspend fun getPokemonSpecies(
        @Path("idOrName") idOrName: String
    ): PokemonSpeciesDto

    @GET("pokemon/{idOrName}/encounters")
    suspend fun getPokemonLocationEncounters(
        @Path("idOrName") idOrName: String
    ): List<PokemonLocationEncounterDto>
}
