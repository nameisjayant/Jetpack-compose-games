package com.nameisjayant.compose_game.features.brick_breaker.utils

data class BrickBreakerGameState(
    val ballX: Float,
    val ballY: Float,
    val ballVX: Float,
    val ballVY: Float,
    val paddleX: Float,
    val bricks: List<Brick>,
    val score: Int = 0,
    val lives: Int = 3,
    val isPlaying: Boolean = false,
    val isGameOver: Boolean = false,
    val isWon: Boolean = false
)
