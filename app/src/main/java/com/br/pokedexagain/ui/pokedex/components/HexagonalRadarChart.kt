package com.br.pokedexagain.ui.pokedex.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.br.pokedexagain.domain.model.PokemonStats
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun HexagonalRadarChart(
    stats: PokemonStats,
    modifier: Modifier = Modifier,
    chartColor: Color = MaterialTheme.colorScheme.primary,
    labelColor: Color = MaterialTheme.colorScheme.onSurfaceVariant
) {
    val textMeasurer = rememberTextMeasurer()
    val statValues = listOf(
        stats.hp.toFloat(),
        stats.attack.toFloat(),
        stats.defense.toFloat(),
        stats.specialAttack.toFloat(),
        stats.specialDefense.toFloat(),
        stats.speed.toFloat()
    )
    val statLabels = listOf("HP", "Attack", "Defense", "Sp. Atk", "Sp. Def", "Speed")
    val maxStatValue = 255f // Max base stat in Pokémon (e.g., Blissey HP)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(240.dp)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val center = Offset(size.width / 2f, size.height / 2f)
            val radius = minOf(size.width, size.height) / 2f * 0.65f
            val levels = 4

            // Draw concentric hexagon grid levels
            val gridColor = labelColor.copy(alpha = 0.2f)
            val gridStroke = Stroke(width = 1.dp.toPx())

            for (level in 1..levels) {
                val currentRadius = radius * (level.toFloat() / levels.toFloat())
                val hexPath = Path().apply {
                    val points = calculateHexagonVertices(center, currentRadius)
                    points.forEachIndexed { index, point ->
                        if (index == 0) moveTo(point.x, point.y)
                        else lineTo(point.x, point.y)
                    }
                    close()
                }
                drawPath(path = hexPath, color = gridColor, style = gridStroke)
            }

            // Draw axes from center to 6 vertices
            val outerVertices = calculateHexagonVertices(center, radius)
            outerVertices.forEach { vertex ->
                drawLine(
                    color = gridColor,
                    start = center,
                    end = vertex,
                    strokeWidth = 1.dp.toPx()
                )
            }

            // Draw filled polygon for pokemon stats
            val normalizedStats = statValues.map { (it / maxStatValue).coerceIn(0f, 1f) }
            val statPolygonPoints = calculateStatPolygonVertices(center, radius, normalizedStats)

            val polygonPath = Path().apply {
                statPolygonPoints.forEachIndexed { index, point ->
                    if (index == 0) moveTo(point.x, point.y)
                    else lineTo(point.x, point.y)
                }
                close()
            }

            // Draw filled polygon area
            drawPath(
                path = polygonPath,
                color = chartColor.copy(alpha = 0.35f),
                style = Fill
            )

            // Draw polygon outline
            drawPath(
                path = polygonPath,
                color = chartColor,
                style = Stroke(width = 2.5.dp.toPx())
            )

            // Draw vertex dots
            statPolygonPoints.forEach { point ->
                drawCircle(
                    color = chartColor,
                    radius = 4.dp.toPx(),
                    center = point
                )
            }

            // Draw labels
            val labelRadius = radius * 1.25f
            val angleStep = (2.0 * Math.PI / 6.0).toFloat()
            statLabels.forEachIndexed { index, label ->
                val angle = (index * angleStep) - (Math.PI / 2.0).toFloat()
                val x = center.x + labelRadius * cos(angle)
                val y = center.y + labelRadius * sin(angle)

                val valueText = "${statValues[index].toInt()}"
                val fullLabel = "$label\n$valueText"

                val textLayoutResult = textMeasurer.measure(
                    text = fullLabel,
                    style = TextStyle(
                        color = labelColor,
                        fontSize = 11.sp
                    )
                )

                val textWidth = textLayoutResult.size.width
                val textHeight = textLayoutResult.size.height

                drawText(
                    textLayoutResult = textLayoutResult,
                    topLeft = Offset(
                        x = x - textWidth / 2f,
                        y = y - textHeight / 2f
                    )
                )
            }
        }
    }
}

fun calculateHexagonVertices(center: Offset, radius: Float): List<Offset> {
    val angleStep = (2.0 * Math.PI / 6.0).toFloat()
    return (0 until 6).map { i ->
        val angle = (i * angleStep) - (Math.PI / 2.0).toFloat()
        Offset(
            x = center.x + radius * cos(angle),
            y = center.y + radius * sin(angle)
        )
    }
}

fun calculateStatPolygonVertices(center: Offset, radius: Float, normalizedStats: List<Float>): List<Offset> {
    val angleStep = (2.0 * Math.PI / 6.0).toFloat()
    return normalizedStats.indices.map { i ->
        val angle = (i * angleStep) - (Math.PI / 2.0).toFloat()
        val r = radius * normalizedStats[i].coerceIn(0f, 1f)
        Offset(
            x = center.x + r * cos(angle),
            y = center.y + r * sin(angle)
        )
    }
}
