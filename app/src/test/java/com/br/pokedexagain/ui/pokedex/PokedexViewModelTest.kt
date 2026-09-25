package com.br.pokedexagain.ui.pokedex

import com.br.pokedexagain.domain.model.PokemonListItem
import com.br.pokedexagain.domain.repository.PokemonRepository
import com.br.pokedexagain.domain.usecase.GetPokemonListUseCase
import com.br.pokedexagain.domain.usecase.SearchPokemonUseCase
import com.br.pokedexagain.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.eq
import org.mockito.kotlin.whenever

@OptIn(ExperimentalCoroutinesApi::class)
class PokedexViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repository: PokemonRepository
    private lateinit var getPokemonListUseCase: GetPokemonListUseCase
    private lateinit var searchPokemonUseCase: SearchPokemonUseCase
    private lateinit var viewModel: PokedexViewModel

    private val samplePokemonList = listOf(
        PokemonListItem(1, "Bulbasaur", "http://example.com/1.png", listOf("Grass", "Poison")),
        PokemonListItem(4, "Charmander", "http://example.com/4.png", listOf("Fire")),
        PokemonListItem(7, "Squirtle", "http://example.com/7.png", listOf("Water")),
        PokemonListItem(25, "Pikachu", "http://example.com/25.png", listOf("Electric"))
    )

    @Before
    fun setUp() = runTest {
        repository = mock(PokemonRepository::class.java)
        getPokemonListUseCase = GetPokemonListUseCase(repository)
        searchPokemonUseCase = SearchPokemonUseCase(repository)

        whenever(repository.getPokemonList(151, 0)).thenReturn(Result.success(samplePokemonList))
    }

    @Test
    fun init_loadsPokemonListSuccessfully() = runTest {
        viewModel = PokedexViewModel(getPokemonListUseCase, searchPokemonUseCase)

        val state = viewModel.uiState.value
        assertTrue(state is PokedexUiState.Success)
        val successState = state as PokedexUiState.Success
        assertEquals(4, successState.pokemonList.size)
        assertEquals("Bulbasaur", successState.pokemonList[0].name)
    }

    @Test
    fun onSearchQueryChanged_filtersPokemonByName() = runTest {
        val filtered = listOf(samplePokemonList[0]) // Bulbasaur
        whenever(repository.searchPokemon(eq("Bulba"), eq(null))).thenReturn(Result.success(filtered))

        viewModel = PokedexViewModel(getPokemonListUseCase, searchPokemonUseCase)
        viewModel.onSearchQueryChanged("Bulba")

        assertEquals("Bulba", viewModel.searchQuery.value)
        val state = viewModel.uiState.value
        assertTrue(state is PokedexUiState.Success)
        val successState = state as PokedexUiState.Success
        assertEquals(1, successState.pokemonList.size)
        assertEquals("Bulbasaur", successState.pokemonList[0].name)
    }

    @Test
    fun onSearchQueryChanged_filtersPokemonByPokedexNumber() = runTest {
        val filtered = listOf(samplePokemonList[1]) // Charmander (#4)
        whenever(repository.searchPokemon(eq("4"), eq(null))).thenReturn(Result.success(filtered))

        viewModel = PokedexViewModel(getPokemonListUseCase, searchPokemonUseCase)
        viewModel.onSearchQueryChanged("4")

        assertEquals("4", viewModel.searchQuery.value)
        val state = viewModel.uiState.value
        assertTrue(state is PokedexUiState.Success)
        val successState = state as PokedexUiState.Success
        assertEquals(1, successState.pokemonList.size)
        assertEquals("Charmander", successState.pokemonList[0].name)
    }

    @Test
    fun onTypeFilterSelected_filtersPokemonByType() = runTest {
        val filtered = listOf(samplePokemonList[2]) // Squirtle
        whenever(repository.searchPokemon(eq(""), eq("Water"))).thenReturn(Result.success(filtered))

        viewModel = PokedexViewModel(getPokemonListUseCase, searchPokemonUseCase)
        viewModel.onTypeFilterSelected("Water")

        assertEquals("Water", viewModel.selectedTypeFilter.value)
        val state = viewModel.uiState.value
        assertTrue(state is PokedexUiState.Success)
        val successState = state as PokedexUiState.Success
        assertEquals(1, successState.pokemonList.size)
        assertEquals("Squirtle", successState.pokemonList[0].name)
    }

    @Test
    fun onTypeFilterSelected_togglesSelectionWhenClickedAgain() = runTest {
        whenever(repository.searchPokemon(eq(""), eq("Fire"))).thenReturn(Result.success(listOf(samplePokemonList[1])))

        viewModel = PokedexViewModel(getPokemonListUseCase, searchPokemonUseCase)
        viewModel.onTypeFilterSelected("Fire")
        assertEquals("Fire", viewModel.selectedTypeFilter.value)

        viewModel.onTypeFilterSelected("Fire")
        assertNull(viewModel.selectedTypeFilter.value)
    }

    @Test
    fun clearSearchQuery_resetsSearchQueryText() = runTest {
        whenever(repository.searchPokemon(eq("Pika"), eq(null))).thenReturn(Result.success(listOf(samplePokemonList[3])))

        viewModel = PokedexViewModel(getPokemonListUseCase, searchPokemonUseCase)
        viewModel.onSearchQueryChanged("Pika")
        assertEquals("Pika", viewModel.searchQuery.value)

        viewModel.clearSearchQuery()
        assertEquals("", viewModel.searchQuery.value)
    }

    @Test
    fun loadPokemon_error_updatesUiStateToError() = runTest {
        whenever(repository.getPokemonList(151, 0)).thenReturn(Result.failure(RuntimeException("Network failure")))

        viewModel = PokedexViewModel(getPokemonListUseCase, searchPokemonUseCase)

        val state = viewModel.uiState.value
        assertTrue(state is PokedexUiState.Error)
        val errorState = state as PokedexUiState.Error
        assertEquals("Network failure", errorState.message)
    }
}
