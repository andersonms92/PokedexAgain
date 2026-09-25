package com.br.pokedexagain.ui.pokedex

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.br.pokedexagain.domain.model.PokemonListItem
import com.br.pokedexagain.domain.usecase.GetPokemonListUseCase
import com.br.pokedexagain.domain.usecase.SearchPokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokedexViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase,
    private val searchPokemonUseCase: SearchPokemonUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<PokedexUiState>(PokedexUiState.Loading)
    val uiState: StateFlow<PokedexUiState> = _uiState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedTypeFilter = MutableStateFlow<String?>(null)
    val selectedTypeFilter: StateFlow<String?> = _selectedTypeFilter.asStateFlow()

    init {
        loadPokemon()
        observeSearchAndFilter()
    }

    fun loadPokemon() {
        viewModelScope.launch {
            _uiState.value = PokedexUiState.Loading
            executeSearchOrGetList(_searchQuery.value, _selectedTypeFilter.value)
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
    }

    fun onTypeFilterSelected(type: String?) {
        if (type == null || _selectedTypeFilter.value.equals(type, ignoreCase = true)) {
            _selectedTypeFilter.value = null
        } else {
            _selectedTypeFilter.value = type
        }
    }

    fun clearSearchQuery() {
        _searchQuery.value = ""
    }

    fun retry() {
        loadPokemon()
    }

    private fun observeSearchAndFilter() {
        viewModelScope.launch {
            combine(_searchQuery, _selectedTypeFilter) { query, type ->
                Pair(query, type)
            }.collectLatest { (query, type) ->
                executeSearchOrGetList(query, type)
            }
        }
    }

    private suspend fun executeSearchOrGetList(query: String, typeFilter: String?) {
        val result = if (query.isBlank() && typeFilter.isNullOrBlank()) {
            getPokemonListUseCase(limit = 151, offset = 0)
        } else {
            searchPokemonUseCase(query = query, typeFilter = typeFilter)
        }

        result.fold(
            onSuccess = { list ->
                _uiState.value = PokedexUiState.Success(pokemonList = list)
            },
            onFailure = { throwable ->
                _uiState.value = PokedexUiState.Error(
                    message = throwable.localizedMessage ?: "Failed to load Pokédex data"
                )
            }
        )
    }
}
