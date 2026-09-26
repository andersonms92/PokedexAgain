package com.br.pokedexagain.ui.pokedex.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.br.pokedexagain.domain.model.PokemonGeneration
import com.br.pokedexagain.ui.theme.PokedexAgainTheme

@Composable
fun GenerationFilterChips(
    selectedGeneration: Int?,
    onGenerationSelected: (Int?) -> Unit,
    modifier: Modifier = Modifier
) {
    val generations = PokemonGeneration.entries.toList()

    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp)
    ) {
        item {
            val isSelected = selectedGeneration == null
            val chipBackgroundColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            val chipContentColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant

            FilterChip(
                selected = isSelected,
                onClick = { onGenerationSelected(null) },
                label = {
                    Text(
                        text = "All Generations",
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                leadingIcon = if (isSelected) {
                    {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Selected",
                            tint = chipContentColor
                        )
                    }
                } else null,
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = chipBackgroundColor,
                    selectedLabelColor = chipContentColor,
                    containerColor = chipBackgroundColor,
                    labelColor = chipContentColor
                )
            )
        }

        items(generations) { gen ->
            val isSelected = selectedGeneration == gen.id
            val chipBackgroundColor = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            val chipContentColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant

            FilterChip(
                selected = isSelected,
                onClick = { onGenerationSelected(gen.id) },
                label = {
                    Text(
                        text = gen.title,
                        style = MaterialTheme.typography.labelMedium
                    )
                },
                leadingIcon = if (isSelected) {
                    {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Selected",
                            tint = chipContentColor
                        )
                    }
                } else null,
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = chipBackgroundColor,
                    selectedLabelColor = chipContentColor,
                    containerColor = chipBackgroundColor,
                    labelColor = chipContentColor
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GenerationFilterChipsPreview() {
    PokedexAgainTheme {
        GenerationFilterChips(
            selectedGeneration = 1,
            onGenerationSelected = {}
        )
    }
}
