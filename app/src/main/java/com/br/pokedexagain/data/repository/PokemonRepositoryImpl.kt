package com.br.pokedexagain.data.repository

import com.br.pokedexagain.data.mapper.extractPokemonIdFromUrl
import com.br.pokedexagain.data.mapper.toDomain
import com.br.pokedexagain.data.remote.api.PokeApiService
import com.br.pokedexagain.domain.model.PokemonDetail
import com.br.pokedexagain.domain.model.PokemonListItem
import com.br.pokedexagain.domain.model.PokemonLocationEncounter
import com.br.pokedexagain.domain.model.PokemonSpeciesInfo
import com.br.pokedexagain.domain.repository.PokemonRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepositoryImpl @Inject constructor(
    private val apiService: PokeApiService
) : PokemonRepository {

    private var cachedList: List<PokemonListItem> = emptyList()

    override suspend fun getPokemonList(limit: Int, offset: Int): Result<List<PokemonListItem>> {
        return runCatching {
            val response = apiService.getPokemonList(limit, offset)
            val domainList = coroutineScope {
                response.results.map { itemDto ->
                    async {
                        val id = extractPokemonIdFromUrl(itemDto.url)
                        val detailDto = runCatching { apiService.getPokemonDetail(id.toString()) }.getOrNull()
                        val types = detailDto?.types?.sortedBy { it.slot }?.map {
                            it.type.name.replaceFirstChar { c -> if (c.isLowerCase()) c.titlecase() else c.toString() }
                        } ?: emptyList()
                        itemDto.toDomain(types = types)
                    }
                }.awaitAll()
            }
            if (offset == 0) {
                cachedList = domainList
            } else {
                cachedList = (cachedList + domainList).distinctBy { it.id }
            }
            domainList
        }
    }

    override suspend fun getGenerationPokemon(generationId: Int): Result<List<PokemonListItem>> {
        return runCatching {
            val allList = if (cachedList.isEmpty()) {
                getPokemonList(limit = 1302, offset = 0).getOrDefault(emptyList())
            } else {
                cachedList
            }
            allList.filter { it.generation == generationId }
        }
    }

    override suspend fun getPokemonDetail(idOrName: String): Result<PokemonDetail> {
        return runCatching {
            val detailDto = apiService.getPokemonDetail(idOrName.lowercase().trim())
            detailDto.toDomain()
        }
    }

    override suspend fun getPokemonSpecies(idOrName: String): Result<PokemonSpeciesInfo> {
        return runCatching {
            val speciesDto = apiService.getPokemonSpecies(idOrName.lowercase().trim())
            speciesDto.toDomain()
        }
    }

    override suspend fun getPokemonLocationEncounters(idOrName: String): Result<List<PokemonLocationEncounter>> {
        return runCatching {
            val encountersDto = apiService.getPokemonLocationEncounters(idOrName.lowercase().trim())
            encountersDto.map { it.toDomain() }
        }
    }

    override suspend fun searchPokemon(query: String, typeFilter: String?, generationFilter: Int?): Result<List<PokemonListItem>> {
        return runCatching {
            val cleanQuery = query.lowercase().trim()
            val listToFilter = if (cachedList.isEmpty()) {
                getPokemonList(limit = 1302, offset = 0).getOrDefault(emptyList())
            } else {
                cachedList
            }

            var filtered = if (cleanQuery.isEmpty()) {
                listToFilter
            } else {
                listToFilter.filter { pokemon ->
                    pokemon.name.lowercase().contains(cleanQuery) ||
                            pokemon.id.toString() == cleanQuery
                }
            }

            if (!typeFilter.isNullOrBlank()) {
                val cleanType = typeFilter.lowercase().trim()
                filtered = filtered.filter { pokemon ->
                    pokemon.types.any { it.lowercase() == cleanType }
                }
            }

            if (generationFilter != null) {
                filtered = filtered.filter { pokemon ->
                    pokemon.generation == generationFilter
                }
            }

            filtered
        }
    }
}
