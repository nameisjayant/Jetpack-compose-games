package com.nameisjayant.compose_game.features.tic_tac_toe.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.nameisjayant.compose_game.ui.theme.DarkCard
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PlayerScoreCard(
    symbol: String,
    score: Int,
    color: Color,
    isActive: Boolean,
    modifier: Modifier = Modifier
) {
    val bgColor = if (isActive) color.copy(alpha = 0.15f) else DarkCard

    Box(
        modifier = modifier
            .width(110.dp)
            .background(bgColor, RoundedCornerShape(16.dp))
            .then(
                if (isActive) Modifier.border(2.dp, color, RoundedCornerShape(16.dp))
                else Modifier
            )
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = symbol,
                color = color,
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "$score",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
