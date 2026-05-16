package com.nameisjayant.compose_game.features.brick_breaker.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.nameisjayant.compose_game.features.brick_breaker.ui.viewmodel.BrickBreakerViewModel
import com.nameisjayant.compose_game.features.brick_breaker.utils.BrickBreakerGameState
import com.nameisjayant.compose_game.ui.theme.AccentPurple
import com.nameisjayant.compose_game.ui.theme.DarkSurface

private val brickColors = listOf(
    Color(0xFFEF4444),
    Color(0xFFF97316),
    Color(0xFFEAB308),
    Color(0xFF22C55E),
    Color(0xFF3B82F6),
    Color(0xFF8B5CF6)
)

@Composable
fun BrickBreakerCanvas(
    gameState: BrickBreakerGameState,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val scaleX = size.width / BrickBreakerViewModel.WORLD_WIDTH
        val scaleY = size.height / BrickBreakerViewModel.WORLD_HEIGHT

        // Background
        drawRect(color = DarkSurface)

        // Subtle bottom danger zone gradient
        drawRect(
            brush = Brush.verticalGradient(
                colors = listOf(Color.Transparent, Color(0x22EF4444)),
                startY = size.height * 0.75f,
                endY = size.height
            )
        )

        // Bricks
        gameState.bricks.filter { it.isAlive }.forEach { brick ->
            val bx = BrickBreakerViewModel.brickLeft(brick.col) * scaleX
            val by = BrickBreakerViewModel.brickTop(brick.row) * scaleY
            val bw = BrickBreakerViewModel.BRICK_WIDTH * scaleX
            val bh = BrickBreakerViewModel.BRICK_HEIGHT * scaleY
            val color = brickColors[brick.row % brickColors.size]

            // Glow shadow
            drawRoundRect(
                color = color.copy(alpha = 0.25f),
                topLeft = Offset(bx - 1f, by + 1f),
                size = Size(bw + 2f, bh + 2f),
                cornerRadius = CornerRadius(5f)
            )
            // Brick body
            drawRoundRect(
                color = color,
                topLeft = Offset(bx, by),
                size = Size(bw, bh),
                cornerRadius = CornerRadius(4f)
            )
            // Glass highlight
            drawRoundRect(
                color = Color.White.copy(alpha = 0.22f),
                topLeft = Offset(bx + 3f, by + 2f),
                size = Size(bw - 6f, bh * 0.45f),
                cornerRadius = CornerRadius(3f)
            )
        }

        // Paddle
        val px = (gameState.paddleX - BrickBreakerViewModel.PADDLE_WIDTH / 2) * scaleX
        val py = (BrickBreakerViewModel.PADDLE_Y - BrickBreakerViewModel.PADDLE_HEIGHT / 2) * scaleY
        val pw = BrickBreakerViewModel.PADDLE_WIDTH * scaleX
        val ph = BrickBreakerViewModel.PADDLE_HEIGHT * scaleY

        // Paddle glow
        drawRoundRect(
            color = AccentPurple.copy(alpha = 0.3f),
            topLeft = Offset(px - 3f, py - 2f),
            size = Size(pw + 6f, ph + 4f),
            cornerRadius = CornerRadius(ph / 2 + 2f)
        )
        // Paddle body
        drawRoundRect(
            color = AccentPurple,
            topLeft = Offset(px, py),
            size = Size(pw, ph),
            cornerRadius = CornerRadius(ph / 2)
        )
        // Paddle highlight
        drawRoundRect(
            color = Color.White.copy(alpha = 0.3f),
            topLeft = Offset(px + pw * 0.1f, py + 1f),
            size = Size(pw * 0.8f, ph * 0.38f),
            cornerRadius = CornerRadius(ph / 2)
        )

        // Ball glow
        val ballCx = gameState.ballX * scaleX
        val ballCy = gameState.ballY * scaleY
        val ballR = BrickBreakerViewModel.BALL_RADIUS * minOf(scaleX, scaleY)

        drawCircle(
            color = Color.White.copy(alpha = 0.15f),
            radius = ballR * 2.2f,
            center = Offset(ballCx, ballCy)
        )
        // Ball body
        drawCircle(Color.White, radius = ballR, center = Offset(ballCx, ballCy))
        // Ball shine
        drawCircle(
            color = Color.White.copy(alpha = 0.55f),
            radius = ballR * 0.38f,
            center = Offset(ballCx - ballR * 0.28f, ballCy - ballR * 0.28f)
        )
    }
}
