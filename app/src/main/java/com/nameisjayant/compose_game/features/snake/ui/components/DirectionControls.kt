package com.nameisjayant.compose_game.features.snake.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nameisjayant.compose_game.features.snake.utils.Direction
import com.nameisjayant.compose_game.ui.theme.DarkCard

@Composable
fun DirectionControls(
    onDirection: (Direction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DPadButton(label = "▲", onClick = { onDirection(Direction.UP) })
        Row(verticalAlignment = Alignment.CenterVertically) {
            DPadButton(label = "◀", onClick = { onDirection(Direction.LEFT) })
            Spacer(modifier = Modifier.width(56.dp))
            DPadButton(label = "▶", onClick = { onDirection(Direction.RIGHT) })
        }
        DPadButton(label = "▼", onClick = { onDirection(Direction.DOWN) })
    }
}

@Composable
private fun DPadButton(
    label: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(56.dp)
            .background(DarkCard, RoundedCornerShape(14.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            color = Color.White,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
    }
}
