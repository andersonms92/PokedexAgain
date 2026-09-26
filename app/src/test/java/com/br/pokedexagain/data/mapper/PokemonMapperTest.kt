package com.br.pokedexagain.data.mapper

import com.br.pokedexagain.data.remote.dto.FlavorTextEntryDto
import com.br.pokedexagain.data.remote.dto.GenusDto
import com.br.pokedexagain.data.remote.dto.NamedApiResourceDto
import com.br.pokedexagain.data.remote.dto.OfficialArtworkDto
import com.br.pokedexagain.data.remote.dto.PokemonAbilityDto
import com.br.pokedexagain.data.remote.dto.PokemonDetailDto
import com.br.pokedexagain.data.remote.dto.PokemonListItemDto
import com.br.pokedexagain.data.remote.dto.PokemonLocationEncounterDto
import com.br.pokedexagain.data.remote.dto.PokemonOtherSpritesDto
import com.br.pokedexagain.data.remote.dto.PokemonSpeciesDto
import com.br.pokedexagain.data.remote.dto.PokemonSpritesDto
import com.br.pokedexagain.data.remote.dto.PokemonStatDto
import com.br.pokedexagain.data.remote.dto.PokemonTypeSlotDto
import com.br.pokedexagain.data.remote.dto.VersionEncounterDetailDto
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class PokemonMapperTest {

    @Test
    fun extractPokemonIdFromUrl_validUrl_returnsCorrectId() {
        val url = "https://pokeapi.co/api/v2/pokemon/25/"
        val id = extractPokemonIdFromUrl(url)
        assertEquals(25, id)
    }

    @Test
    fun pokemonListItemDto_toDomain_mapsCorrectly() {
        val dto = PokemonListItemDto(
            name = "pikachu",
            url = "https://pokeapi.co/api/v2/pokemon/25/"
        )
        val domain = dto.toDomain()

        assertEquals(25, domain.id)
        assertEquals("Pikachu", domain.name)
        assertTrue(domain.imageUrl.contains("25.png"))
        assertEquals(listOf("Electric"), domain.types)
    }

    @Test
    fun pokemonDetailDto_toDomain_mapsStatsAndDetailsCorrectly() {
        val dto = PokemonDetailDto(
            id = 25,
            name = "pikachu",
            height = 4,
            weight = 60,
            types = listOf(
                PokemonTypeSlotDto(slot = 1, type = NamedApiResourceDto("electric", ""))
            ),
            stats = listOf(
                PokemonStatDto(35, 0, NamedApiResourceDto("hp", "")),
                PokemonStatDto(55, 0, NamedApiResourceDto("attack", "")),
                PokemonStatDto(40, 0, NamedApiResourceDto("defense", "")),
                PokemonStatDto(50, 0, NamedApiResourceDto("special-attack", "")),
                PokemonStatDto(50, 0, NamedApiResourceDto("special-defense", "")),
                PokemonStatDto(90, 0, NamedApiResourceDto("speed", ""))
            ),
            sprites = PokemonSpritesDto(
                frontDefault = "http://example.com/front.png",
                other = PokemonOtherSpritesDto(
                    officialArtwork = OfficialArtworkDto("http://example.com/artwork.png", "http://example.com/shiny.png")
                )
            ),
            abilities = listOf(
                PokemonAbilityDto(NamedApiResourceDto("static", ""), isHidden = false)
            )
        )

        val domain = dto.toDomain()

        assertEquals(25, domain.id)
        assertEquals("Pikachu", domain.name)
        assertEquals(0.4, domain.heightInMeters, 0.01)
        assertEquals(6.0, domain.weightInKg, 0.01)
        assertEquals(listOf("Electric"), domain.types)
        assertEquals(listOf("Static"), domain.abilities)
        assertEquals("http://example.com/artwork.png", domain.imageUrl)
        assertEquals("http://example.com/shiny.png", domain.shinyImageUrl)

        // Verify 6 base stats
        assertEquals(35, domain.stats.hp)
        assertEquals(55, domain.stats.attack)
        assertEquals(40, domain.stats.defense)
        assertEquals(50, domain.stats.specialAttack)
        assertEquals(50, domain.stats.specialDefense)
        assertEquals(90, domain.stats.speed)
        assertEquals(320, domain.stats.totalStats)
    }

    @Test
    fun pokemonSpeciesDto_toDomain_cleansFlavorTextAndGenus() {
        val dto = PokemonSpeciesDto(
            id = 25,
            name = "pikachu",
            captureRate = 190,
            flavorTextEntries = listOf(
                FlavorTextEntryDto(
                    flavorText = "When it runs,\nit generates\u000celectricity.",
                    language = NamedApiResourceDto("en", "")
                )
            ),
            genera = listOf(
                GenusDto(genus = "Mouse Pokémon", language = NamedApiResourceDto("en", ""))
            ),
            habitat = NamedApiResourceDto("forest", ""),
            isLegendary = false,
            isMythical = false
        )

        val domain = dto.toDomain()

        assertEquals(25, domain.id)
        assertEquals("Mouse Pokémon", domain.genus)
        assertEquals("When it runs, it generates electricity.", domain.flavorText)
        assertEquals(190, domain.captureRate)
        assertEquals("Forest", domain.habitat)
        assertFalse(domain.isLegendary)
        assertFalse(domain.isMythical)
    }

    @Test
    fun pokemonLocationEncounterDto_toDomain_formatsLocationNames() {
        val dto = PokemonLocationEncounterDto(
            locationArea = NamedApiResourceDto("kanto-route-1-area", ""),
            versionDetails = listOf(
                VersionEncounterDetailDto(
                    maxChance = 50,
                    version = NamedApiResourceDto("red", "")
                )
            )
        )

        val domain = dto.toDomain()

        assertEquals("kanto-route-1-area", domain.locationAreaName)
        assertEquals("Kanto Route 1 Area", domain.formattedLocationName)
        assertEquals(listOf("Red"), domain.versions)
    }
}
