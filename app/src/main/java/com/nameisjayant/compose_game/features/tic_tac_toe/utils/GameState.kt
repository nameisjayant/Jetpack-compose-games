package com.nameisjayant.compose_game.features.tic_tac_toe.utils

data class GameState(
    val board: List<List<Player?>>,
    val currentPlayer: Player,
    val winner: Player? = null,
    val isDraw: Boolean = false,
    val winningCells: List<Pair<Int, Int>> = emptyList()
)