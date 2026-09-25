package com.br.pokedexagain.domain.usecase

import com.br.pokedexagain.domain.model.PokemonDetail
import com.br.pokedexagain.domain.model.PokemonListItem
import com.br.pokedexagain.domain.model.PokemonLocationEncounter
import com.br.pokedexagain.domain.model.PokemonSpeciesInfo
import com.br.pokedexagain.domain.model.PokemonStats
import com.br.pokedexagain.domain.repository.PokemonRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

class PokemonUseCasesTest {

    private lateinit var repository: PokemonRepository

    @Before
    fun setUp() {
        repository = mock(PokemonRepository::class.java)
    }

    @Test
    fun getPokemonListUseCase_invokesRepository() = runTest {
        val mockList = listOf(PokemonListItem(1, "Bulbasaur", "http://example.com/1.png"))
        whenever(repository.getPokemonList(100, 0)).thenReturn(Result.success(mockList))

        val useCase = GetPokemonListUseCase(repository)
        val result = useCase(100, 0)

        assertTrue(result.isSuccess)
        assertEquals(mockList, result.getOrNull())
    }

    @Test
    fun searchPokemonUseCase_invokesRepository() = runTest {
        val mockList = listOf(PokemonListItem(25, "Pikachu", "http://example.com/25.png"))
        whenever(repository.searchPokemon("pika", null)).thenReturn(Result.success(mockList))

        val useCase = SearchPokemonUseCase(repository)
        val result = useCase("pika")

        assertTrue(result.isSuccess)
        assertEquals(mockList, result.getOrNull())
    }

    @Test
    fun getPokemonDetailUseCase_invokesRepository() = runTest {
        val mockDetail = PokemonDetail(
            id = 25,
            name = "Pikachu",
            imageUrl = "http://example.com/25.png",
            shinyImageUrl = null,
            heightInMeters = 0.4,
            weightInKg = 6.0,
            types = listOf("Electric"),
            abilities = listOf("Static"),
            stats = PokemonStats(hp = 35, attack = 55, defense = 40, specialAttack = 50, specialDefense = 50, speed = 90)
        )
        whenever(repository.getPokemonDetail("25")).thenReturn(Result.success(mockDetail))

        val useCase = GetPokemonDetailUseCase(repository)
        val result = useCase("25")

        assertTrue(result.isSuccess)
        assertEquals(mockDetail, result.getOrNull())
    }

    @Test
    fun getPokemonSpeciesUseCase_invokesRepository() = runTest {
        val mockSpecies = PokemonSpeciesInfo(
            id = 25,
            genus = "Mouse Pokémon",
            flavorText = "Generates electricity.",
            captureRate = 190,
            habitat = "Forest",
            isLegendary = false,
            isMythical = false
        )
        whenever(repository.getPokemonSpecies("25")).thenReturn(Result.success(mockSpecies))

        val useCase = GetPokemonSpeciesUseCase(repository)
        val result = useCase("25")

        assertTrue(result.isSuccess)
        assertEquals(mockSpecies, result.getOrNull())
    }

    @Test
    fun getPokemonLocationEncountersUseCase_invokesRepository() = runTest {
        val mockEncounters = listOf(PokemonLocationEncounter("viridian-forest-area", "Viridian Forest Area", listOf("Red")))
        whenever(repository.getPokemonLocationEncounters("25")).thenReturn(Result.success(mockEncounters))

        val useCase = GetPokemonLocationEncountersUseCase(repository)
        val result = useCase("25")

        assertTrue(result.isSuccess)
        assertEquals(mockEncounters, result.getOrNull())
    }

    @Test
    fun getPokemonStatsUseCase_extractsStatsFromDetail() = runTest {
        val stats = PokemonStats(hp = 35, attack = 55, defense = 40, specialAttack = 50, specialDefense = 50, speed = 90)
        val mockDetail = PokemonDetail(
            id = 25,
            name = "Pikachu",
            imageUrl = "http://example.com/25.png",
            shinyImageUrl = null,
            heightInMeters = 0.4,
            weightInKg = 6.0,
            types = listOf("Electric"),
            abilities = listOf("Static"),
            stats = stats
        )
        whenever(repository.getPokemonDetail("25")).thenReturn(Result.success(mockDetail))

        val useCase = GetPokemonStatsUseCase(repository)
        val result = useCase("25")

        assertTrue(result.isSuccess)
        assertEquals(stats, result.getOrNull())
    }
}
