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
            "grass" -> Color(0x33000000)
            "fire" -> Color(0x33000000)
            "water" -> Color(0x33000000)
            "bug" -> Color(0x33000000)
            "electric" -> Color(0x33000000)
            "normal" -> Color(0x33000000)
            "poison" -> Color(0x33000000)
            "ground" -> Color(0x33000000)
            "rock" -> Color(0x33000000)
            "psychic" -> Color(0x33000000)
            "ice" -> Color(0x33000000)
            "dragon" -> Color(0x33000000)
            "ghost" -> Color(0x33000000)
            "dark" -> Color(0x33000000)
            "steel" -> Color(0x33000000)
            "fairy" -> Color(0x33000000)
            "fighting" -> Color(0x33000000)
            "flying" -> Color(0x33000000)
            else -> Color(0x33000000)
        }
    }

    fun getTypesForPokemon(id: Int, name: String? = null): List<String> {
        val mappedTypes = gen1TypesMap[id]
        if (!mappedTypes.isNullOrEmpty()) {
            return mappedTypes
        }
        // Fallback search by name if id is unknown
        if (!name.isNullOrBlank()) {
            val nameLower = name.lowercase().trim()
            val entry = gen1TypesMapByName[nameLower]
            if (entry != null) return entry
        }
        return listOf("Normal")
    }

    val allTypes = listOf(
        "Grass", "Fire", "Water", "Electric", "Bug", "Poison",
        "Ground", "Rock", "Psychic", "Ice", "Dragon", "Ghost",
        "Normal", "Fighting", "Flying", "Steel", "Fairy", "Dark"
    )

    private val gen1TypesMap: Map<Int, List<String>> = mapOf(
        1 to listOf("Grass", "Poison"),
        2 to listOf("Grass", "Poison"),
        3 to listOf("Grass", "Poison"),
        4 to listOf("Fire"),
        5 to listOf("Fire"),
        6 to listOf("Fire", "Flying"),
        7 to listOf("Water"),
        8 to listOf("Water"),
        9 to listOf("Water"),
        10 to listOf("Bug"),
        11 to listOf("Bug"),
        12 to listOf("Bug", "Flying"),
        13 to listOf("Bug", "Poison"),
        14 to listOf("Bug", "Poison"),
        15 to listOf("Bug", "Poison"),
        16 to listOf("Normal", "Flying"),
        17 to listOf("Normal", "Flying"),
        18 to listOf("Normal", "Flying"),
        19 to listOf("Normal"),
        20 to listOf("Normal"),
        21 to listOf("Normal", "Flying"),
        22 to listOf("Normal", "Flying"),
        23 to listOf("Poison"),
        24 to listOf("Poison"),
        25 to listOf("Electric"),
        26 to listOf("Electric"),
        27 to listOf("Ground"),
        28 to listOf("Ground"),
        29 to listOf("Poison"),
        30 to listOf("Poison"),
        31 to listOf("Poison", "Ground"),
        32 to listOf("Poison"),
        33 to listOf("Poison"),
        34 to listOf("Poison", "Ground"),
        35 to listOf("Fairy"),
        36 to listOf("Fairy"),
        37 to listOf("Fire"),
        38 to listOf("Fire"),
        39 to listOf("Normal", "Fairy"),
        40 to listOf("Normal", "Fairy"),
        41 to listOf("Poison", "Flying"),
        42 to listOf("Poison", "Flying"),
        43 to listOf("Grass", "Poison"),
        44 to listOf("Grass", "Poison"),
        45 to listOf("Grass", "Poison"),
        46 to listOf("Bug", "Grass"),
        47 to listOf("Bug", "Grass"),
        48 to listOf("Bug", "Poison"),
        49 to listOf("Bug", "Poison"),
        50 to listOf("Ground"),
        51 to listOf("Ground"),
        52 to listOf("Normal"),
        53 to listOf("Normal"),
        54 to listOf("Water"),
        55 to listOf("Water"),
        56 to listOf("Fighting"),
        57 to listOf("Fighting"),
        58 to listOf("Fire"),
        59 to listOf("Fire"),
        60 to listOf("Water"),
        61 to listOf("Water"),
        62 to listOf("Water", "Fighting"),
        63 to listOf("Psychic"),
        64 to listOf("Psychic"),
        65 to listOf("Psychic"),
        66 to listOf("Fighting"),
        67 to listOf("Fighting"),
        68 to listOf("Fighting"),
        69 to listOf("Grass", "Poison"),
        70 to listOf("Grass", "Poison"),
        71 to listOf("Grass", "Poison"),
        72 to listOf("Water", "Poison"),
        73 to listOf("Water", "Poison"),
        74 to listOf("Rock", "Ground"),
        75 to listOf("Rock", "Ground"),
        76 to listOf("Rock", "Ground"),
        77 to listOf("Fire"),
        78 to listOf("Fire"),
        79 to listOf("Water", "Psychic"),
        80 to listOf("Water", "Psychic"),
        81 to listOf("Electric", "Steel"),
        82 to listOf("Electric", "Steel"),
        83 to listOf("Normal", "Flying"),
        84 to listOf("Normal", "Flying"),
        85 to listOf("Normal", "Flying"),
        86 to listOf("Water"),
        87 to listOf("Water", "Ice"),
        88 to listOf("Poison"),
        89 to listOf("Poison"),
        90 to listOf("Water"),
        91 to listOf("Water", "Ice"),
        92 to listOf("Ghost", "Poison"),
        93 to listOf("Ghost", "Poison"),
        94 to listOf("Ghost", "Poison"),
        95 to listOf("Rock", "Ground"),
        96 to listOf("Psychic"),
        97 to listOf("Psychic"),
        98 to listOf("Water"),
        99 to listOf("Water"),
        100 to listOf("Electric"),
        101 to listOf("Electric"),
        102 to listOf("Grass", "Psychic"),
        103 to listOf("Grass", "Psychic"),
        104 to listOf("Ground"),
        105 to listOf("Ground"),
        106 to listOf("Fighting"),
        107 to listOf("Fighting"),
        108 to listOf("Normal"),
        109 to listOf("Poison"),
        110 to listOf("Poison"),
        111 to listOf("Ground", "Rock"),
        112 to listOf("Ground", "Rock"),
        113 to listOf("Normal"),
        114 to listOf("Grass"),
        115 to listOf("Normal"),
        116 to listOf("Water"),
        117 to listOf("Water"),
        118 to listOf("Water"),
        119 to listOf("Water"),
        120 to listOf("Water"),
        121 to listOf("Water", "Psychic"),
        122 to listOf("Psychic", "Fairy"),
        123 to listOf("Bug", "Flying"),
        124 to listOf("Ice", "Psychic"),
        125 to listOf("Electric"),
        126 to listOf("Fire"),
        127 to listOf("Bug"),
        128 to listOf("Normal"),
        129 to listOf("Water"),
        130 to listOf("Water", "Flying"),
        131 to listOf("Water", "Ice"),
        132 to listOf("Normal"),
        133 to listOf("Normal"),
        134 to listOf("Water"),
        135 to listOf("Electric"),
        136 to listOf("Fire"),
        137 to listOf("Normal"),
        138 to listOf("Rock", "Water"),
        139 to listOf("Rock", "Water"),
        140 to listOf("Rock", "Water"),
        141 to listOf("Rock", "Water"),
        142 to listOf("Rock", "Flying"),
        143 to listOf("Normal"),
        144 to listOf("Ice", "Flying"),
        145 to listOf("Electric", "Flying"),
        146 to listOf("Fire", "Flying"),
        147 to listOf("Dragon"),
        148 to listOf("Dragon"),
        149 to listOf("Dragon", "Flying"),
        150 to listOf("Psychic"),
        151 to listOf("Psychic")
    )

    private val gen1TypesMapByName: Map<String, List<String>> = gen1TypesMap.entries.associate { (id, types) ->
        val name = when (id) {
            1 -> "bulbasaur"; 2 -> "ivysaur"; 3 -> "venusaur"; 4 -> "charmander"; 5 -> "charmeleon"
            6 -> "charizard"; 7 -> "squirtle"; 8 -> "wartortle"; 9 -> "blastoise"; 10 -> "caterpie"
            11 -> "metapod"; 12 -> "butterfree"; 13 -> "weedle"; 14 -> "kakuna"; 15 -> "beedrill"
            16 -> "pidgey"; 17 -> "pidgeotto"; 18 -> "pidgeot"; 19 -> "rattata"; 20 -> "raticate"
            21 -> "spearow"; 22 -> "fearow"; 23 -> "ekans"; 24 -> "arbok"; 25 -> "pikachu"
            26 -> "raichu"; 27 -> "sandshrew"; 28 -> "sandslash"; 29 -> "nidoran-f"; 30 -> "nidorina"
            31 -> "nidoqueen"; 32 -> "nidoran-m"; 33 -> "nidorino"; 34 -> "nidoking"; 35 -> "clefairy"
            36 -> "clefable"; 37 -> "vulpix"; 38 -> "ninetales"; 39 -> "jigglypuff"; 40 -> "wigglytuff"
            41 -> "zubat"; 42 -> "golbat"; 43 -> "oddish"; 44 -> "gloom"; 45 -> "vileplume"
            46 -> "paras"; 47 -> "parasect"; 48 -> "venonat"; 49 -> "venomoth"; 50 -> "diglett"
            51 -> "dugtrio"; 52 -> "meowth"; 53 -> "persian"; 54 -> "psyduck"; 55 -> "golduck"
            56 -> "mankey"; 57 -> "primeape"; 58 -> "growlithe"; 59 -> "arcanine"; 60 -> "poliwag"
            61 -> "poliwhirl"; 62 -> "poliwrath"; 63 -> "abra"; 64 -> "kadabra"; 65 -> "alakazam"
            66 -> "machop"; 67 -> "machoke"; 68 -> "machamp"; 69 -> "bellsprout"; 70 -> "weepinbell"
            71 -> "victreebel"; 72 -> "tentacool"; 73 -> "tentacruel"; 74 -> "geodude"; 75 -> "graveler"
            76 -> "golem"; 77 -> "ponyta"; 78 -> "rapidash"; 79 -> "slowpoke"; 80 -> "slowbro"
            81 -> "magnemite"; 82 -> "magneton"; 83 -> "farfetchd"; 84 -> "doduo"; 85 -> "dodrio"
            86 -> "seel"; 87 -> "dewgong"; 88 -> "grimer"; 89 -> "muk"; 90 -> "shellder"
            91 -> "cloyster"; 92 -> "gastly"; 93 -> "haunter"; 94 -> "gengar"; 95 -> "onix"
            96 -> "drowzee"; 97 -> "hypno"; 98 -> "krabby"; 99 -> "kingler"; 100 -> "voltorb"
            101 -> "electrode"; 102 -> "exeggcute"; 103 -> "exeggutor"; 104 -> "cubone"; 105 -> "marowak"
            106 -> "hitmonlee"; 107 -> "hitmonchan"; 108 -> "lickitung"; 109 -> "koffing"; 110 -> "weezing"
            111 -> "rhyhorn"; 112 -> "rhydon"; 113 -> "chansey"; 114 -> "tangela"; 115 -> "kangaskhan"
            116 -> "horsea"; 117 -> "seadra"; 118 -> "goldeen"; 119 -> "seaking"; 120 -> "staryu"
            121 -> "starmie"; 122 -> "mr-mime"; 123 -> "scyther"; 124 -> "jynx"; 125 -> "electabuzz"
            126 -> "magmar"; 127 -> "pinsir"; 128 -> "tauros"; 129 -> "magikarp"; 130 -> "gyarados"
            131 -> "lapras"; 132 -> "ditto"; 133 -> "eevee"; 134 -> "vaporeon"; 135 -> "jolteon"
            136 -> "flareon"; 137 -> "porygon"; 138 -> "omanyte"; 139 -> "omastar"; 140 -> "kabuto"
            141 -> "kabutops"; 142 -> "aerodactyl"; 143 -> "snorlax"; 144 -> "articuno"; 145 -> "zapdos"
            146 -> "moltres"; 147 -> "dratini"; 148 -> "dragonair"; 149 -> "dragonite"; 150 -> "mewtwo"
            151 -> "mew"
            else -> ""
        }
        name to types
    }
}
