package com.nameisjayant.compose_game.features.tic_tac_toe.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.nameisjayant.compose_game.features.tic_tac_toe.utils.GameState
import com.nameisjayant.compose_game.features.tic_tac_toe.utils.Player
import com.nameisjayant.compose_game.features.tic_tac_toe.utils.TicTacToeHelpers

class TicTacToeViewModel : ViewModel() {

    var gameState by mutableStateOf(GameState(TicTacToeHelpers.createEmptyBoard(), Player.X))
        private set

    var xScore by mutableIntStateOf(0)
        private set

    var oScore by mutableIntStateOf(0)
        private set

    var drawScore by mutableIntStateOf(0)
        private set

    private var scoreTracked = false

    fun makeMove(row: Int, col: Int) {
        val newState = TicTacToeHelpers.makeMove(gameState, row, col)
        gameState = newState
        if (!scoreTracked && (newState.winner != null || newState.isDraw)) {
            scoreTracked = true
            when (newState.winner) {
                Player.X -> xScore++
                Player.O -> oScore++
                null -> drawScore++
            }
        }
    }

    fun resetGame() {
        gameState = GameState(TicTacToeHelpers.createEmptyBoard(), Player.X)
        scoreTracked = false
    }
}
