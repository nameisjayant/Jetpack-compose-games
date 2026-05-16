package com.nameisjayant.compose_game.features.brick_breaker.utils

data class Brick(
    val row: Int,
    val col: Int,
    val isAlive: Boolean = true
)
