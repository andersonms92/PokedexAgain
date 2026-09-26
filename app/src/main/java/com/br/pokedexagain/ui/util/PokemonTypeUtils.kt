package com.br.pokedexagain.ui.util

import androidx.compose.ui.graphics.Color

object PokemonTypeUtils {

    fun getTypeCardBackgroundColor(type: String?): Color {
        return when (type?.lowercase()?.trim()) {
            "grass" -> Color(0xFF48D0B0)
            "fire" -> Color(0xFFFB6C6C)
            "water" -> Color(0xFF76BDFE)
            "bug" -> Color(0xFFA7D87D)
            "electric" -> Color(0xFFFFD86F)
            "normal" -> Color(0xFFC6C6A7)
            "poison" -> Color(0xFFB57EDC)
            "ground" -> Color(0xFFE0C068)
            "rock" -> Color(0xFFC5B078)
            "psychic" -> Color(0xFFFA92B2)
            "ice" -> Color(0xFF98D8D8)
            "dragon" -> Color(0xFF9A70F8)
            "ghost" -> Color(0xFF7558A4)
            "dark" -> Color(0xFF8D7868)
            "steel" -> Color(0xFFB8B8D0)
            "fairy" -> Color(0xFFEE99AC)
            "fighting" -> Color(0xFFD56723)
            "flying" -> Color(0xFFA890F0)
            else -> Color(0xFF90A4AE)
        }
    }

    fun getTypeBadgeBackgroundColor(type: String?): Color {
        return when (type?.lowercase()?.trim()) {
            "grass", "fire", "water", "bug", "electric", "normal",
            "poison", "ground", "rock", "psychic", "ice", "dragon",
            "ghost", "dark", "steel", "fairy", "fighting", "flying" -> Color(0x33000000)
            else -> Color(0x33000000)
        }
    }
}
