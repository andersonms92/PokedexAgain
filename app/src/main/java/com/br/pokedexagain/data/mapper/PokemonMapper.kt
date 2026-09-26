package com.br.pokedexagain.data.mapper

import android.util.Log
import com.br.pokedexagain.data.remote.dto.PokemonDetailDto
import com.br.pokedexagain.data.remote.dto.PokemonListItemDto
import com.br.pokedexagain.data.remote.dto.PokemonLocationEncounterDto
import com.br.pokedexagain.data.remote.dto.PokemonSpeciesDto
import com.br.pokedexagain.data.remote.dto.PokemonStatDto
import com.br.pokedexagain.domain.model.PokemonDetail
import com.br.pokedexagain.domain.model.PokemonGeneration
import com.br.pokedexagain.domain.model.PokemonListItem
import com.br.pokedexagain.domain.model.PokemonLocationEncounter
import com.br.pokedexagain.domain.model.PokemonSpeciesInfo
import com.br.pokedexagain.domain.model.PokemonStats

fun PokemonListItemDto.toDomain(types: List<String> = emptyList()): PokemonListItem {
    val id = extractPokemonIdFromUrl(url)
    val formattedName = name.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
    val generationObj = PokemonGeneration.fromId(id)
    return PokemonListItem(
        id = id,
        name = formattedName,
        imageUrl = imageUrl,
        types = types,
        generation = generationObj.id,
        generationName = generationObj.title
    )
}

fun extractPokemonIdFromUrl(url: String): Int {
    return try {
        val trimmed = url.trimEnd('/')
        trimmed.substringAfterLast('/').toInt()
    } catch (e: Exception) {
       Log.e("Exception", e.toString())
    }
}

fun PokemonDetailDto.toDomain(): PokemonDetail {
    val formattedName = name.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    val artworkUrl = sprites.other?.officialArtwork?.frontDefault
        ?: sprites.frontDefault
        ?: "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"
    val shinyUrl = sprites.other?.officialArtwork?.frontShiny

    val typeList = types.sortedBy { it.slot }.map {
        it.type.name.replaceFirstChar { c -> if (c.isLowerCase()) c.titlecase() else c.toString() }
    }

    val abilityList = abilities?.map {
        it.ability.name.replace("-", " ").split(" ")
            .joinToString(" ") { word -> word.replaceFirstChar { c -> if (c.isLowerCase()) c.titlecase() else c.toString() } }
    } ?: emptyList()

    val generationObj = PokemonGeneration.fromId(id)

    return PokemonDetail(
        id = id,
        name = formattedName,
        imageUrl = artworkUrl,
        shinyImageUrl = shinyUrl,
        heightInMeters = height / 10.0,
        weightInKg = weight / 10.0,
        types = typeList,
        abilities = abilityList,
        stats = extractPokemonStats(stats),
        generation = generationObj.id,
        generationName = generationObj.title
    )
}

fun extractPokemonStats(statDtos: List<PokemonStatDto>): PokemonStats {
    var hp = 0
    var attack = 0
    var defense = 0
    var specialAttack = 0
    var specialDefense = 0
    var speed = 0

    for (statDto in statDtos) {
        when (statDto.stat.name.lowercase()) {
            "hp" -> hp = statDto.baseStat
            "attack" -> attack = statDto.baseStat
            "defense" -> defense = statDto.baseStat
            "special-attack", "special_attack" -> specialAttack = statDto.baseStat
            "special-defense", "special_defense" -> specialDefense = statDto.baseStat
            "speed" -> speed = statDto.baseStat
        }
    }

    return PokemonStats(
        hp = hp,
        attack = attack,
        defense = defense,
        specialAttack = specialAttack,
        specialDefense = specialDefense,
        speed = speed
    )
}

fun PokemonSpeciesDto.toDomain(): PokemonSpeciesInfo {
    val englishGenus = genera.firstOrNull { it.language.name == "en" }?.genus ?: ""
    val rawFlavorText = flavorTextEntries.firstOrNull { it.language.name == "en" }?.flavorText ?: ""
    val cleanFlavorText = rawFlavorText
        .replace("\n", " ")
        .replace("\r", " ")
        .replace("\u000c", " ")
        .replace("\\s+".toRegex(), " ")
        .trim()

    val formattedHabitat = habitat?.name?.replace("-", " ")
        ?.split(" ")
        ?.joinToString(" ") { word -> word.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() } }

    return PokemonSpeciesInfo(
        id = id,
        genus = englishGenus,
        flavorText = cleanFlavorText,
        captureRate = captureRate,
        habitat = formattedHabitat,
        isLegendary = isLegendary ?: false,
        isMythical = isMythical ?: false
    )
}

fun PokemonLocationEncounterDto.toDomain(): PokemonLocationEncounter {
    val formattedLocation = locationArea.name.replace("-", " ")
        .split(" ")
        .joinToString(" ") { word -> word.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() } }

    val versionList = versionDetails.map { detail ->
        detail.version.name.replace("-", " ")
            .split(" ")
            .joinToString(" ") { word -> word.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() } }
    }.distinct()

    return PokemonLocationEncounter(
        locationAreaName = locationArea.name,
        formattedLocationName = formattedLocation,
        versions = versionList
    )
}
