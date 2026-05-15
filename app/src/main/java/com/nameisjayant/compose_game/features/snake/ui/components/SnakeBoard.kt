package com.nameisjayant.compose_game.features.snake.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.nameisjayant.compose_game.features.snake.ui.viewmodel.SnakeViewModel
import com.nameisjayant.compose_game.features.snake.utils.SnakeGameState
import com.nameisjayant.compose_game.ui.theme.DarkBorder
import com.nameisjayant.compose_game.ui.theme.DarkSurface
import com.nameisjayant.compose_game.ui.theme.PlayerXColor
import com.nameisjayant.compose_game.ui.theme.SnakeBodyColor
import com.nameisjayant.compose_game.ui.theme.SnakeHeadColor

@Composable
fun SnakeBoard(
    gameState: SnakeGameState,
    modifier: Modifier = Modifier
) {
    val gridSize = SnakeViewModel.GRID_SIZE

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clip(RoundedCornerShape(16.dp))
            .background(DarkSurface)
    ) {
        val cellSize = size.width / gridSize

        // Subtle grid lines
        for (i in 0..gridSize) {
            val pos = i * cellSize
            drawLine(DarkBorder, Offset(pos, 0f), Offset(pos, size.height), strokeWidth = 0.5f)
            drawLine(DarkBorder, Offset(0f, pos), Offset(size.width, pos), strokeWidth = 0.5f)
        }

        // Food
        val food = gameState.food
        val foodPadding = cellSize * 0.15f
        drawCircle(
            color = PlayerXColor,
            radius = cellSize / 2 - foodPadding,
            center = Offset(food.x * cellSize + cellSize / 2, food.y * cellSize + cellSize / 2)
        )

        // Snake body (draw tail-to-head so head renders on top)
        gameState.snake.reversed().forEachIndexed { reversedIndex, pos ->
            val isHead = reversedIndex == gameState.snake.size - 1
            val padding = if (isHead) cellSize * 0.08f else cellSize * 0.14f
            val color = if (isHead) SnakeHeadColor else SnakeBodyColor
            drawRoundRect(
                color = color,
                topLeft = Offset(pos.x * cellSize + padding, pos.y * cellSize + padding),
                size = Size(cellSize - padding * 2, cellSize - padding * 2),
                cornerRadius = CornerRadius(4.dp.toPx())
            )
        }
    }
}
