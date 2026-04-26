package com.nameisjayant.compose_game.features.tic_tac_toe.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import com.nameisjayant.compose_game.features.tic_tac_toe.utils.GameState


@Composable
fun TicTacToeBoard(
    state: GameState, onCellClick: (Int, Int) -> Unit
) {
    Column {
        for (i in 0..2) {
            Row {
                for (j in 0..2) {
                    TicTacToeCell(
                        value = state.board[i][j], onClick = { onCellClick(i, j) })
                }
            }
        }
    }
}