package com.br.pokedexagain.data.remote.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PokemonSpeciesDto(
    @Json(name = "id") val id: Int,
    @Json(name = "name") val name: String,
    @Json(name = "capture_rate") val captureRate: Int?,
    @Json(name = "flavor_text_entries") val flavorTextEntries: List<FlavorTextEntryDto>,
    @Json(name = "genera") val genera: List<GenusDto>,
    @Json(name = "habitat") val habitat: NamedApiResourceDto?,
    @Json(name = "is_legendary") val isLegendary: Boolean?,
    @Json(name = "is_mythical") val isMythical: Boolean?
)

@JsonClass(generateAdapter = true)
data class FlavorTextEntryDto(
    @Json(name = "flavor_text") val flavorText: String,
    @Json(name = "language") val language: NamedApiResourceDto,
    @Json(name = "version") val version: NamedApiResourceDto? = null
)

@JsonClass(generateAdapter = true)
data class GenusDto(
    @Json(name = "genus") val genus: String,
    @Json(name = "language") val language: NamedApiResourceDto
)
