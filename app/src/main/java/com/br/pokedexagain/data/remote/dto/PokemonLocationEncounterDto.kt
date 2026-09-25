package com.br.pokedexagain.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonLocationEncounterDto(
    @Json(name = "location_area") val locationArea: NamedApiResourceDto,
    @Json(name = "version_details") val versionDetails: List<VersionEncounterDetailDto> = emptyList()
)

@JsonClass(generateAdapter = true)
data class VersionEncounterDetailDto(
    @Json(name = "max_chance") val maxChance: Int,
    @Json(name = "version") val version: NamedApiResourceDto
)
