package com.br.pokedexagain.ui.pokedex

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.pokedexagain.domain.usecase.GetPokemonDetailUseCase
import com.br.pokedexagain.domain.usecase.GetPokemonLocationEncountersUseCase
import com.br.pokedexagain.domain.usecase.GetPokemonSpeciesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val getPokemonDetailUseCase: GetPokemonDetailUseCase,
    private val getPokemonSpeciesUseCase: GetPokemonSpeciesUseCase,
    private val getPokemonLocationEncountersUseCase: GetPokemonLocationEncountersUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<PokemonDetailUiState>(PokemonDetailUiState.Loading)
    val uiState: StateFlow<PokemonDetailUiState> = _uiState.asStateFlow()

    private val pokemonId: Int = savedStateHandle.get<Int>("pokemonId") ?: 1
    private val pokemonName: String = savedStateHandle.get<String>("pokemonName") ?: pokemonId.toString()

    init {
        loadPokemonData(pokemonId.toString())
    }

    fun loadPokemonData(idOrName: String) {
        viewModelScope.launch {
            _uiState.value = PokemonDetailUiState.Loading

            val detailDeferred = async { getPokemonDetailUseCase(idOrName) }
            val speciesDeferred = async { getPokemonSpeciesUseCase(idOrName) }
            val locationsDeferred = async { getPokemonLocationEncountersUseCase(idOrName) }

            val detailResult = detailDeferred.await()
            val speciesResult = speciesDeferred.await()
            val locationsResult = locationsDeferred.await()

            detailResult.fold(
                onSuccess = { detail ->
                    val species = speciesResult.getOrNull()
                    val locations = locationsResult.getOrNull() ?: emptyList()
                    _uiState.value = PokemonDetailUiState.Success(
                        detail = detail,
                        species = species,
                        locations = locations
                    )
                },
                onFailure = { throwable ->
                    _uiState.value = PokemonDetailUiState.Error(
                        message = throwable.localizedMessage ?: "Failed to load Pokémon details"
                    )
                }
            )
        }
    }

    fun retry() {
        loadPokemonData(pokemonId.toString())
    }
}
