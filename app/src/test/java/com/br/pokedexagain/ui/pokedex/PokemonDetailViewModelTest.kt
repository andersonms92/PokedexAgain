package com.br.pokedexagain.ui.pokedex

import androidx.lifecycle.SavedStateHandle
import com.br.pokedexagain.domain.model.PokemonDetail
import com.br.pokedexagain.domain.model.PokemonLocationEncounter
import com.br.pokedexagain.domain.model.PokemonSpeciesInfo
import com.br.pokedexagain.domain.model.PokemonStats
import com.br.pokedexagain.domain.repository.PokemonRepository
import com.br.pokedexagain.domain.usecase.GetPokemonDetailUseCase
import com.br.pokedexagain.domain.usecase.GetPokemonLocationEncountersUseCase
import com.br.pokedexagain.domain.usecase.GetPokemonSpeciesUseCase
import com.br.pokedexagain.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class PokemonDetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repository: PokemonRepository
    private lateinit var getPokemonDetailUseCase: GetPokemonDetailUseCase
    private lateinit var getPokemonSpeciesUseCase: GetPokemonSpeciesUseCase
    private lateinit var getPokemonLocationEncountersUseCase: GetPokemonLocationEncountersUseCase
    private lateinit var viewModel: PokemonDetailViewModel

    private val sampleDetail = PokemonDetail(
        id = 1,
        name = "Bulbasaur",
        imageUrl = "http://example.com/1.png",
        shinyImageUrl = null,
        heightInMeters = 0.7,
        weightInKg = 6.9,
        types = listOf("Grass", "Poison"),
        abilities = listOf("Overgrow"),
        stats = PokemonStats(45, 49, 49, 65, 65, 45)
    )

    private val sampleSpecies = PokemonSpeciesInfo(
        id = 1,
        genus = "Seed Pokémon",
        flavorText = "A strange seed was planted on its back at birth.",
        captureRate = 45,
        habitat = "grassland",
        isLegendary = false,
        isMythical = false
    )

    private val sampleLocations = listOf(
        PokemonLocationEncounter("kanto-route-1-area", "Kanto Route 1", listOf("red"))
    )

    @Before
    fun setUp() = runTest {
        repository = mock(PokemonRepository::class.java)
        getPokemonDetailUseCase = GetPokemonDetailUseCase(repository)
        getPokemonSpeciesUseCase = GetPokemonSpeciesUseCase(repository)
        getPokemonLocationEncountersUseCase = GetPokemonLocationEncountersUseCase(repository)

        whenever(repository.getPokemonDetail("1")).thenReturn(Result.success(sampleDetail))
        whenever(repository.getPokemonSpecies("1")).thenReturn(Result.success(sampleSpecies))
        whenever(repository.getPokemonLocationEncounters("1")).thenReturn(Result.success(sampleLocations))
    }

    @Test
    fun init_loadsPokemonDetailSuccessfully() = runTest {
        val savedStateHandle = SavedStateHandle(mapOf("pokemonId" to 1, "pokemonName" to "Bulbasaur"))
        viewModel = PokemonDetailViewModel(
            getPokemonDetailUseCase,
            getPokemonSpeciesUseCase,
            getPokemonLocationEncountersUseCase,
            savedStateHandle
        )

        val state = viewModel.uiState.value
        assertTrue(state is PokemonDetailUiState.Success)
        val success = state as PokemonDetailUiState.Success
        assertEquals("Bulbasaur", success.detail.name)
        assertEquals("Seed Pokémon", success.species?.genus)
        assertEquals(1, success.locations.size)
        assertEquals("Kanto Route 1", success.locations[0].formattedLocationName)
    }

    @Test
    fun loadPokemonData_failure_updatesUiStateToError() = runTest {
        whenever(repository.getPokemonDetail("1")).thenReturn(Result.failure(RuntimeException("Not found")))

        val savedStateHandle = SavedStateHandle(mapOf("pokemonId" to 1))
        viewModel = PokemonDetailViewModel(
            getPokemonDetailUseCase,
            getPokemonSpeciesUseCase,
            getPokemonLocationEncountersUseCase,
            savedStateHandle
        )

        val state = viewModel.uiState.value
        assertTrue(state is PokemonDetailUiState.Error)
        val error = state as PokemonDetailUiState.Error
        assertEquals("Not found", error.message)
    }
}
