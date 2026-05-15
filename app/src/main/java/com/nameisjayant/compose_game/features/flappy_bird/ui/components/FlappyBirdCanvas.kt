package com.nameisjayant.compose_game.features.flappy_bird.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.nameisjayant.compose_game.features.flappy_bird.ui.viewmodel.FlappyBirdViewModel
import com.nameisjayant.compose_game.features.flappy_bird.utils.FlappyBirdGameState
import com.nameisjayant.compose_game.features.flappy_bird.utils.FlappyBirdPipe

@Composable
fun FlappyBirdCanvas(
    gameState: FlappyBirdGameState,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val scaleX = size.width / FlappyBirdViewModel.WORLD_WIDTH
        val scaleY = size.height / FlappyBirdViewModel.WORLD_HEIGHT
        val groundY = FlappyBirdViewModel.GROUND_Y * scaleY

        // Sky gradient
        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(Color(0xFF0EA5E9), Color(0xFF7DD3FC)),
                startY = 0f,
                endY = groundY
            )
        )

        // Pipes
        gameState.pipes.forEach { drawPipe(it, scaleX, scaleY, groundY) }

        // Ground strip
        drawRect(
            color = Color(0xFF78350F),
            topLeft = Offset(0f, groundY),
            size = Size(size.width, size.height - groundY)
        )
        // Grass edge
        drawRect(
            color = Color(0xFF4ADE80),
            topLeft = Offset(0f, groundY - 6f * scaleY),
            size = Size(size.width, 6f * scaleY)
        )

        // Bird
        val cx = FlappyBirdViewModel.BIRD_X * scaleX
        val cy = gameState.birdY * scaleY
        val r = FlappyBirdViewModel.BIRD_RADIUS * minOf(scaleX, scaleY)

        // Body
        drawCircle(Color(0xFFFBBF24), radius = r, center = Offset(cx, cy))
        // Belly highlight
        drawCircle(Color(0xFFFEF3C7), radius = r * 0.55f, center = Offset(cx + r * 0.1f, cy + r * 0.15f))
        // Eye white
        drawCircle(Color(0xFFFFFFFF), radius = r * 0.3f, center = Offset(cx + r * 0.3f, cy - r * 0.2f))
        // Eye pupil
        drawCircle(Color(0xFF1C1917), radius = r * 0.15f, center = Offset(cx + r * 0.38f, cy - r * 0.2f))
        // Beak
        drawPath(
            path = Path().apply {
                moveTo(cx + r * 0.65f, cy - r * 0.05f)
                lineTo(cx + r * 1.3f, cy + r * 0.1f)
                lineTo(cx + r * 0.65f, cy + r * 0.35f)
                close()
            },
            color = Color(0xFFEA580C)
        )
    }
}

private fun DrawScope.drawPipe(
    pipe: FlappyBirdPipe,
    scaleX: Float,
    scaleY: Float,
    groundY: Float
) {
    val x = pipe.x * scaleX
    val w = FlappyBirdViewModel.PIPE_WIDTH * scaleX
    val gapTop = (pipe.gapCenterY - FlappyBirdViewModel.PIPE_GAP / 2) * scaleY
    val gapBottom = (pipe.gapCenterY + FlappyBirdViewModel.PIPE_GAP / 2) * scaleY
    val capH = 14f * scaleY
    val capExtra = 5f * scaleX
    val pipeColor = Color(0xFF4ADE80)
    val capColor = Color(0xFF16A34A)

    // Top pipe body
    if (gapTop - capH > 0f) {
        drawRect(color = pipeColor, topLeft = Offset(x, 0f), size = Size(w, gapTop - capH))
    }
    // Top pipe cap
    drawRect(color = capColor, topLeft = Offset(x - capExtra, gapTop - capH), size = Size(w + capExtra * 2, capH))

    // Bottom pipe body
    if (gapBottom + capH < groundY) {
        drawRect(color = pipeColor, topLeft = Offset(x, gapBottom + capH), size = Size(w, groundY - gapBottom - capH))
    }
    // Bottom pipe cap
    drawRect(color = capColor, topLeft = Offset(x - capExtra, gapBottom), size = Size(w + capExtra * 2, capH))
}
