package com.br.pokedexagain.ui.pokedex.components

import androidx.compose.ui.geometry.Offset
import org.junit.Assert.assertEquals
import org.junit.Test

class HexagonalRadarChartMathTest {

    @Test
    fun calculateHexagonVertices_returnsSixVertices() {
        val center = Offset(100f, 100f)
        val radius = 50f
        val vertices = calculateHexagonVertices(center, radius)

        assertEquals(6, vertices.size)
        // First vertex should be straight up at (center.x, center.y - radius)
        assertEquals(100f, vertices[0].x, 0.001f)
        assertEquals(50f, vertices[0].y, 0.001f)
    }

    @Test
    fun calculateStatPolygonVertices_scalesCorrectly() {
        val center = Offset(0f, 0f)
        val radius = 100f
        val normalizedStats = listOf(1f, 0.5f, 0f, 1f, 0.5f, 0f)
        val vertices = calculateStatPolygonVertices(center, radius, normalizedStats)

        assertEquals(6, vertices.size)
        // Stat 0 (HP) at top (angle -90 deg): x = 0, y = -100 * 1 = -100
        assertEquals(0f, vertices[0].x, 0.001f)
        assertEquals(-100f, vertices[0].y, 0.001f)
    }
}
