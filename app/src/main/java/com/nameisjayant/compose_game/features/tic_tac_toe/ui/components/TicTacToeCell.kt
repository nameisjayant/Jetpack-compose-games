package com.nameisjayant.compose_game.features.tic_tac_toe.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nameisjayant.compose_game.features.tic_tac_toe.utils.Player

@Composable
fun TicTacToeCell(value: Player?, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(80.dp)
            .border(1.dp, Color.Black)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = value?.name ?: "",
            fontSize = 24.sp
        )
    }
}