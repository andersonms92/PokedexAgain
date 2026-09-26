package com.br.pokedexagain.domain.model

enum class PokemonGeneration(
    val id: Int,
    val title: String,
    val startId: Int,
    val endId: Int
) {
    GEN_1(1, "Gen 1 (Kanto)", 1, 151),
    GEN_2(2, "Gen 2 (Johto)", 152, 251),
    GEN_3(3, "Gen 3 (Hoenn)", 252, 386),
    GEN_4(4, "Gen 4 (Sinnoh)", 387, 493),
    GEN_5(5, "Gen 5 (Unova)", 494, 649),
    GEN_6(6, "Gen 6 (Kalos)", 650, 721),
    GEN_7(7, "Gen 7 (Alola)", 722, 809),
    GEN_8(8, "Gen 8 (Galar)", 810, 905),
    GEN_9(9, "Gen 9 (Paldea)", 906, 1025);

    companion object {
        fun fromId(id: Int): PokemonGeneration {
            return entries.find { id in it.startId..it.endId } ?: if (id > 1025) GEN_9 else GEN_1
        }
        
        fun fromTitle(title: String?): PokemonGeneration? {
            return entries.find { it.title.equals(title, ignoreCase = true) }
        }
    }
}
