package com.nameisjayant.compose_game.features.flappy_bird.utils

data class FlappyBirdGameState(
    val birdY: Float,
    val birdVelocity: Float = 0f,
    val pipes: List<FlappyBirdPipe> = emptyList(),
    val score: Int = 0,
    val isPlaying: Boolean = false,
    val isGameOver: Boolean = false
)
