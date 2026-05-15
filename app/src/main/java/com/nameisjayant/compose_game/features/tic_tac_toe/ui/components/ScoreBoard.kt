package com.nameisjayant.compose_game.features.tic_tac_toe.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.nameisjayant.compose_game.features.tic_tac_toe.utils.Player
import com.nameisjayant.compose_game.ui.theme.PlayerOColor
import com.nameisjayant.compose_game.ui.theme.PlayerXColor

@Composable
fun ScoreBoard(
    xScore: Int,
    oScore: Int,
    drawScore: Int,
    currentPlayer: Player,
    isGameOver: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        PlayerScoreCard(
            symbol = "X",
            score = xScore,
            color = PlayerXColor,
            isActive = !isGameOver && currentPlayer == Player.X
        )
        DrawsCounter(count = drawScore)
        PlayerScoreCard(
            symbol = "O",
            score = oScore,
            color = PlayerOColor,
            isActive = !isGameOver && currentPlayer == Player.O
        )
    }
}
