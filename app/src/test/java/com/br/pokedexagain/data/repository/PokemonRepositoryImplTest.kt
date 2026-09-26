package com.br.pokedexagain.data.repository

import com.br.pokedexagain.data.remote.api.PokeApiService
import com.br.pokedexagain.data.remote.dto.NamedApiResourceDto
import com.br.pokedexagain.data.remote.dto.PokemonDetailDto
import com.br.pokedexagain.data.remote.dto.PokemonListItemDto
import com.br.pokedexagain.data.remote.dto.PokemonListResponseDto
import com.br.pokedexagain.data.remote.dto.PokemonLocationEncounterDto
import com.br.pokedexagain.data.remote.dto.PokemonSpeciesDto
import com.br.pokedexagain.data.remote.dto.PokemonSpritesDto
import com.br.pokedexagain.data.remote.dto.PokemonTypeSlotDto
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class PokemonRepositoryImplTest {

    private lateinit var apiService: PokeApiService
    private lateinit var repository: PokemonRepositoryImpl

    @Before
    fun setUp() {
        apiService = mock(PokeApiService::class.java)
        repository = PokemonRepositoryImpl(apiService)
    }

    @Test
    fun getPokemonList_success_returnsMappedDomainList() = runTest {
        val mockResponse = PokemonListResponseDto(
            count = 2,
            next = null,
            previous = null,
            results = listOf(
                PokemonListItemDto("bulbasaur", "https://pokeapi.co/api/v2/pokemon/1/"),
                PokemonListItemDto("ivysaur", "https://pokeapi.co/api/v2/pokemon/2/")
            )
        )
        whenever(apiService.getPokemonList(100, 0)).thenReturn(mockResponse)

        val detail1 = PokemonDetailDto(
            id = 1,
            name = "bulbasaur",
            height = 7,
            weight = 69,
            types = listOf(PokemonTypeSlotDto(1, NamedApiResourceDto("grass", ""))),
            stats = emptyList(),
            sprites = PokemonSpritesDto(frontDefault = "http://example.com/1.png")
        )
        val detail2 = PokemonDetailDto(
            id = 2,
            name = "ivysaur",
            height = 10,
            weight = 130,
            types = listOf(PokemonTypeSlotDto(1, NamedApiResourceDto("poison", ""))),
            stats = emptyList(),
            sprites = PokemonSpritesDto(frontDefault = "http://example.com/2.png")
        )
        whenever(apiService.getPokemonDetail("1")).thenReturn(detail1)
        whenever(apiService.getPokemonDetail("2")).thenReturn(detail2)

        val result = repository.getPokemonList(100, 0)

        assertTrue(result.isSuccess)
        val list = result.getOrNull()!!
        assertEquals(2, list.size)
        assertEquals(1, list[0].id)
        assertEquals("Bulbasaur", list[0].name)
        assertEquals(listOf("Grass", "Poison"), list[0].types)
        assertEquals(2, list[1].id)
        assertEquals("Ivysaur", list[1].name)
        assertEquals(listOf("Grass", "Poison"), list[1].types)
    }

    @Test
    fun getPokemonDetail_success_returnsMappedDetail() = runTest {
        val mockDetailDto = PokemonDetailDto(
            id = 1,
            name = "bulbasaur",
            height = 7,
            weight = 69,
            types = emptyList(),
            stats = emptyList(),
            sprites = PokemonSpritesDto(frontDefault = "http://example.com/1.png")
        )
        whenever(apiService.getPokemonDetail("1")).thenReturn(mockDetailDto)

        val result = repository.getPokemonDetail("1")

        assertTrue(result.isSuccess)
        val detail = result.getOrNull()!!
        assertEquals(1, detail.id)
        assertEquals("Bulbasaur", detail.name)
        assertEquals(0.7, detail.heightInMeters, 0.01)
        assertEquals(6.9, detail.weightInKg, 0.01)
    }

    @Test
    fun getPokemonSpecies_success_returnsSpeciesInfo() = runTest {
        val mockSpeciesDto = PokemonSpeciesDto(
            id = 1,
            name = "bulbasaur",
            captureRate = 45,
            flavorTextEntries = emptyList(),
            genera = emptyList(),
            habitat = NamedApiResourceDto("grassland", ""),
            isLegendary = false,
            isMythical = false
        )
        whenever(apiService.getPokemonSpecies("1")).thenReturn(mockSpeciesDto)

        val result = repository.getPokemonSpecies("1")

        assertTrue(result.isSuccess)
        val species = result.getOrNull()!!
        assertEquals(1, species.id)
        assertEquals(45, species.captureRate)
        assertEquals("Grassland", species.habitat)
    }

    @Test
    fun getPokemonLocationEncounters_success_returnsList() = runTest {
        val mockEncounters = listOf(
            PokemonLocationEncounterDto(
                locationArea = NamedApiResourceDto("cerulean-city-area", ""),
                versionDetails = emptyList()
            )
        )
        whenever(apiService.getPokemonLocationEncounters("1")).thenReturn(mockEncounters)

        val result = repository.getPokemonLocationEncounters("1")

        assertTrue(result.isSuccess)
        val encounters = result.getOrNull()!!
        assertEquals(1, encounters.size)
        assertEquals("Cerulean City Area", encounters[0].formattedLocationName)
    }
}
