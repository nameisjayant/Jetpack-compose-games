package com.nameisjayant.compose_game.features.tic_tac_toe.ui.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.nameisjayant.compose_game.features.tic_tac_toe.utils.Player

private val XColor = Color(0xFFFF6B6B)
private val OColor = Color(0xFF4DD0E1)

@Composable
fun TicTacToeCell(
    value: Player?,
    isWinningCell: Boolean,
    isEnabled: Boolean,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val pressScale by animateFloatAsState(
        targetValue = if (isPressed) 0.88f else 1f,
        animationSpec = spring(Spring.DampingRatioMediumBouncy, Spring.StiffnessHigh),
        label = "press_scale"
    )

    val symbolScale = remember { Animatable(if (value != null) 1f else 0f) }
    LaunchedEffect(value) {
        if (value != null) {
            symbolScale.animateTo(
                1f,
                spring(dampingRatio = Spring.DampingRatioMediumBouncy, stiffness = Spring.StiffnessMedium)
            )
        } else {
            symbolScale.snapTo(0f)
        }
    }

    val bgColor = when {
        isWinningCell && value == Player.X -> XColor.copy(alpha = 0.22f)
        isWinningCell && value == Player.O -> OColor.copy(alpha = 0.22f)
        else -> Color.Transparent
    }

    Box(
        modifier = Modifier
            .size(100.dp)
            .scale(pressScale)
            .background(bgColor)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                enabled = isEnabled
            ) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (value != null) {
            Canvas(
                modifier = Modifier
                    .size(60.dp)
                    .scale(symbolScale.value)
            ) {
                val padding = 10.dp.toPx()
                val strokeWidth = 7.dp.toPx()
                when (value) {
                    Player.X -> {
                        drawLine(
                            color = XColor,
                            start = Offset(padding, padding),
                            end = Offset(size.width - padding, size.height - padding),
                            strokeWidth = strokeWidth,
                            cap = StrokeCap.Round
                        )
                        drawLine(
                            color = XColor,
                            start = Offset(size.width - padding, padding),
                            end = Offset(padding, size.height - padding),
                            strokeWidth = strokeWidth,
                            cap = StrokeCap.Round
                        )
                    }
                    Player.O -> {
                        drawCircle(
                            color = OColor,
                            radius = size.width / 2 - padding,
                            style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                        )
                    }
                }
            }
        }
    }
}
