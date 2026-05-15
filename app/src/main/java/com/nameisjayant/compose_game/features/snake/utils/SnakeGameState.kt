package com.nameisjayant.compose_game.features.snake.utils

data class SnakeGameState(
    val snake: List<SnakePosition>,
    val food: SnakePosition,
    val direction: Direction,
    val score: Int = 0,
    val isGameOver: Boolean = false,
    val isPlaying: Boolean = false
)
