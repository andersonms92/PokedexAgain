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
import com.br.pokedexagain.ui.theme.PokedexAgainTheme
import com.br.pokedexagain.ui.util.PokemonTypeUtils

private val POKEMON_TYPES = listOf(
    "Grass", "Fire", "Water", "Electric", "Bug", "Poison",
    "Ground", "Rock", "Psychic", "Ice", "Dragon", "Ghost",
    "Normal", "Fighting", "Flying", "Steel", "Fairy", "Dark"
)

@Composable
fun TypeFilterChips(
    selectedType: String?,
    onTypeSelected: (String?) -> Unit,
    modifier: Modifier = Modifier
) {
    val types = listOf("All") + POKEMON_TYPES

    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp)
    ) {
        items(types) { type ->
            val isAll = type == "All"
            val isSelected = if (isAll) selectedType == null else selectedType.equals(type, ignoreCase = true)

            val chipBackgroundColor = if (isSelected) {
                if (isAll) MaterialTheme.colorScheme.primary
                else PokemonTypeUtils.getTypeCardBackgroundColor(type)
            } else {
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            }

            val chipContentColor = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant

            FilterChip(
                selected = isSelected,
                onClick = {
                    if (isAll) {
                        onTypeSelected(null)
                    } else {
                        onTypeSelected(type)
                    }
                },
                label = {
                    Text(
                        text = type,
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
fun TypeFilterChipsPreview() {
    PokedexAgainTheme {
        TypeFilterChips(
            selectedType = "Grass",
            onTypeSelected = {}
        )
    }
}
