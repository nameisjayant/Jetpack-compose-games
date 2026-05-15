package com.nameisjayant.compose_game.features.tic_tac_toe.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.nameisjayant.compose_game.features.tic_tac_toe.utils.GameState
import com.nameisjayant.compose_game.ui.theme.GridLineColor

@Composable
fun TicTacToeBoard(
    state: GameState,
    onCellClick: (Int, Int) -> Unit
) {
    val isGameOver = state.winner != null || state.isDraw

    Box(modifier = Modifier.size(300.dp)) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val cellSize = size.width / 3
            val strokeWidth = 4.dp.toPx()
            val inset = 18.dp.toPx()

            drawLine(GridLineColor, Offset(cellSize, inset), Offset(cellSize, size.height - inset), strokeWidth, cap = StrokeCap.Round)
            drawLine(GridLineColor, Offset(cellSize * 2, inset), Offset(cellSize * 2, size.height - inset), strokeWidth, cap = StrokeCap.Round)
            drawLine(GridLineColor, Offset(inset, cellSize), Offset(size.width - inset, cellSize), strokeWidth, cap = StrokeCap.Round)
            drawLine(GridLineColor, Offset(inset, cellSize * 2), Offset(size.width - inset, cellSize * 2), strokeWidth, cap = StrokeCap.Round)
        }

        Column {
            for (i in 0..2) {
                Row {
                    for (j in 0..2) {
                        TicTacToeCell(
                            value = state.board[i][j],
                            isWinningCell = state.winningCells.contains(i to j),
                            isEnabled = !isGameOver && state.board[i][j] == null,
                            onClick = { onCellClick(i, j) }
                        )
                    }
                }
            }
        }
    }
}
