package com.nameisjayant.compose_game.features.tic_tac_toe.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.nameisjayant.compose_game.features.tic_tac_toe.ui.components.TicTacToeBoard
import com.nameisjayant.compose_game.features.tic_tac_toe.utils.GameState
import com.nameisjayant.compose_game.features.tic_tac_toe.utils.Player
import com.nameisjayant.compose_game.features.tic_tac_toe.utils.TicTacToeHelpers


@Composable
fun TicTacToeScreen(
    modifier: Modifier = Modifier
) {

    var gameState by remember {
        mutableStateOf(
            GameState(
                board = TicTacToeHelpers.createEmptyBoard(),
                currentPlayer = Player.X
            )
        )
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text("Tic Tac Toe")

        TicTacToeBoard(gameState) { row, col ->
            gameState = TicTacToeHelpers.makeMove(gameState, row, col)
        }

        Text(
            when {
                gameState.winner != null -> "Winner: ${gameState.winner}"
                gameState.isDraw -> "Draw!"
                else -> "Turn: ${gameState.currentPlayer}"
            }
        )

        Button(onClick = {
            gameState = GameState(TicTacToeHelpers.createEmptyBoard(), Player.X)
        }) {
            Text("Reset")
        }
    }
}