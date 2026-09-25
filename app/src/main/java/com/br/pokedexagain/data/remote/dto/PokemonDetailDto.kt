package com.br.pokedexagain.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonDetailDto(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "height") val height: Int,
    @Json(name = "weight") val weight: Int,
    @Json(name = "types") val types: List<PokemonTypeSlotDto>,
    @Json(name = "stats") val stats: List<PokemonStatDto>,
    @Json(name = "sprites") val sprites: PokemonSpritesDto,
    @Json(name = "abilities") val abilities: List<PokemonAbilityDto>? = emptyList()
)

@JsonClass(generateAdapter = true)
data class PokemonTypeSlotDto(
    @Json(name = "slot") val slot: Int,
    @Json(name = "type") val type: NamedApiResourceDto
)

@JsonClass(generateAdapter = true)
data class NamedApiResourceDto(
    @Json(name = "name") val name: String,
    @Json(name = "url") val url: String
)

@JsonClass(generateAdapter = true)
data class PokemonStatDto(
    @Json(name = "base_stat") val baseStat: Int,
    @Json(name = "effort") val effort: Int,
    @Json(name = "stat") val stat: NamedApiResourceDto
)

@JsonClass(generateAdapter = true)
data class PokemonSpritesDto(
    @Json(name = "front_default") val frontDefault: String?,
    @Json(name = "other") val other: PokemonOtherSpritesDto? = null
)

@JsonClass(generateAdapter = true)
data class PokemonOtherSpritesDto(
    @Json(name = "official-artwork") val officialArtwork: OfficialArtworkDto? = null
)

@JsonClass(generateAdapter = true)
data class OfficialArtworkDto(
    @Json(name = "front_default") val frontDefault: String?,
    @Json(name = "front_shiny") val frontShiny: String? = null
)

@JsonClass(generateAdapter = true)
data class PokemonAbilityDto(
    @Json(name = "ability") val ability: NamedApiResourceDto,
    @Json(name = "is_hidden") val isHidden: Boolean
)
