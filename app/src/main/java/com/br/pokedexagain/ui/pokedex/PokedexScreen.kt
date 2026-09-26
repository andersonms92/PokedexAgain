package com.br.pokedexagain.ui.pokedex

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.SearchOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.br.pokedexagain.domain.model.PokemonListItem
import com.br.pokedexagain.ui.pokedex.components.GenerationFilterChips
import com.br.pokedexagain.ui.pokedex.components.PokedexSearchBar
import com.br.pokedexagain.ui.pokedex.components.PokemonCard
import com.br.pokedexagain.ui.pokedex.components.TypeFilterChips
import com.br.pokedexagain.ui.theme.PokedexAgainTheme

@Composable
fun PokedexScreen(
    onPokemonClick: (PokemonListItem) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PokedexViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val selectedTypeFilter by viewModel.selectedTypeFilter.collectAsStateWithLifecycle()
    val selectedGenerationFilter by viewModel.selectedGenerationFilter.collectAsStateWithLifecycle()

    PokedexScreenContent(
        uiState = uiState,
        searchQuery = searchQuery,
        selectedTypeFilter = selectedTypeFilter,
        selectedGenerationFilter = selectedGenerationFilter,
        onSearchQueryChange = viewModel::onSearchQueryChanged,
        onClearSearchQuery = viewModel::clearSearchQuery,
        onTypeFilterSelected = viewModel::onTypeFilterSelected,
        onGenerationFilterSelected = viewModel::onGenerationFilterSelected,
        onPokemonClick = onPokemonClick,
        onRetry = viewModel::retry,
        modifier = modifier
    )
}

@Composable
fun PokedexScreenContent(
    uiState: PokedexUiState,
    searchQuery: String,
    selectedTypeFilter: String?,
    selectedGenerationFilter: Int?,
    onSearchQueryChange: (String) -> Unit,
    onClearSearchQuery: () -> Unit,
    onTypeFilterSelected: (String?) -> Unit,
    onGenerationFilterSelected: (Int?) -> Unit,
    onPokemonClick: (PokemonListItem) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Screen Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Column {
                    Text(
                        text = "Pokédex",
                        style = MaterialTheme.typography.headlineLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 32.sp
                        )
                    )
                    Text(
                        text = "Search for Pokémon by name or PokéDex number",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Search Bar
            PokedexSearchBar(
                query = searchQuery,
                onQueryChange = onSearchQueryChange,
                onClearQuery = onClearSearchQuery,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            // Generation Filter Chips
            GenerationFilterChips(
                selectedGeneration = selectedGenerationFilter,
                onGenerationSelected = onGenerationFilterSelected,
                modifier = Modifier.fillMaxWidth()
            )

            // Type Filter Chips
            TypeFilterChips(
                selectedType = selectedTypeFilter,
                onTypeSelected = onTypeFilterSelected,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Main Content Area based on UI State
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f),
                contentAlignment = Alignment.Center
            ) {
                when (uiState) {
                    is PokedexUiState.Loading -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            CircularProgressIndicator(
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Catching Pokémon...",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    is PokedexUiState.Error -> {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.padding(32.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.ErrorOutline,
                                contentDescription = "Error",
                                tint = MaterialTheme.colorScheme.error,
                                modifier = Modifier.size(64.dp)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = uiState.message,
                                style = MaterialTheme.typography.bodyLarge,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(onClick = onRetry) {
                                Text(text = "Try Again")
                            }
                        }
                    }

                    is PokedexUiState.Success -> {
                        if (uiState.pokemonList.isEmpty()) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.padding(32.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.SearchOff,
                                    contentDescription = "No Results",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.size(64.dp)
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = "No Pokémon found",
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = if (searchQuery.isNotBlank()) {
                                        "No results matching '$searchQuery'"
                                    } else {
                                        "No Pokémon available for selected filters"
                                    },
                                    style = MaterialTheme.typography.bodyMedium,
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        } else {
                            LazyVerticalGrid(
                                columns = GridCells.Adaptive(minSize = 160.dp),
                                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                                modifier = Modifier.fillMaxSize()
                            ) {
                                items(
                                    items = uiState.pokemonList,
                                    key = { pokemon -> pokemon.id }
                                ) { pokemon ->
                                    PokemonCard(
                                        pokemon = pokemon,
                                        onPokemonClick = onPokemonClick
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PokedexScreenPreview() {
    PokedexAgainTheme {
        PokedexScreenContent(
            uiState = PokedexUiState.Success(
                pokemonList = listOf(
                    PokemonListItem(1, "Bulbasaur", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/1.png", listOf("Grass", "Poison")),
                    PokemonListItem(4, "Charmander", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/4.png", listOf("Fire")),
                    PokemonListItem(7, "Squirtle", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/7.png", listOf("Water")),
                    PokemonListItem(25, "Pikachu", "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/25.png", listOf("Electric"))
                )
            ),
            searchQuery = "",
            selectedTypeFilter = null,
            selectedGenerationFilter = null,
            onSearchQueryChange = {},
            onClearSearchQuery = {},
            onTypeFilterSelected = {},
            onGenerationFilterSelected = {},
            onPokemonClick = {},
            onRetry = {}
        )
    }
}
