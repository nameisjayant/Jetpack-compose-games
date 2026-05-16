package com.nameisjayant.compose_game.features.brick_breaker.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nameisjayant.compose_game.features.brick_breaker.utils.Brick
import com.nameisjayant.compose_game.features.brick_breaker.utils.BrickBreakerGameState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs

class BrickBreakerViewModel : ViewModel() {

    companion object {
        const val WORLD_WIDTH = 400f
        const val WORLD_HEIGHT = 600f
        const val BALL_RADIUS = 8f
        const val PADDLE_WIDTH = 80f
        const val PADDLE_HEIGHT = 12f
        const val PADDLE_Y = 550f
        const val BRICK_COLS = 8
        const val BRICK_ROWS = 6
        const val BRICK_WIDTH = 45f
        const val BRICK_HEIGHT = 18f
        const val BRICK_H_GAP = 4f
        const val BRICK_V_GAP = 4f
        const val BRICK_SIDE_PADDING = 6f
        const val BRICK_TOP = 50f
        private const val BALL_START_X = 200f
        private const val BALL_START_Y = 390f
        private const val INITIAL_VX = 3f
        private const val INITIAL_VY = -6f
        private const val INITIAL_LIVES = 3
        private const val GAME_TICK_MS = 16L

        fun brickLeft(col: Int) = BRICK_SIDE_PADDING + col * (BRICK_WIDTH + BRICK_H_GAP)
        fun brickTop(row: Int) = BRICK_TOP + row * (BRICK_HEIGHT + BRICK_V_GAP)
    }

    var gameState by mutableStateOf(buildInitialState())
        private set

    var highScore by mutableIntStateOf(0)
        private set

    private var gameJob: Job? = null

    fun startGame() {
        gameJob?.cancel()
        gameState = buildInitialState().copy(isPlaying = true)
        gameJob = viewModelScope.launch {
            while (gameState.isPlaying && !gameState.isGameOver && !gameState.isWon) {
                delay(GAME_TICK_MS)
                tick()
            }
        }
    }

    fun movePaddleTo(worldX: Float) {
        val clamped = worldX.coerceIn(PADDLE_WIDTH / 2, WORLD_WIDTH - PADDLE_WIDTH / 2)
        gameState = gameState.copy(paddleX = clamped)
    }

    private fun tick() {
        val s = gameState

        var bx = s.ballX + s.ballVX
        var by = s.ballY + s.ballVY
        var vx = s.ballVX
        var vy = s.ballVY

        // Wall bounces
        if (bx - BALL_RADIUS <= 0f) { bx = BALL_RADIUS; vx = -vx }
        if (bx + BALL_RADIUS >= WORLD_WIDTH) { bx = WORLD_WIDTH - BALL_RADIUS; vx = -vx }
        if (by - BALL_RADIUS <= 0f) { by = BALL_RADIUS; vy = -vy }

        // Paddle collision (only when ball is moving downward)
        val pLeft = s.paddleX - PADDLE_WIDTH / 2
        val pRight = s.paddleX + PADDLE_WIDTH / 2
        val pTop = PADDLE_Y - PADDLE_HEIGHT / 2
        if (vy > 0 && by + BALL_RADIUS >= pTop && by <= PADDLE_Y && bx >= pLeft && bx <= pRight) {
            by = pTop - BALL_RADIUS
            val hitFraction = (bx - s.paddleX) / (PADDLE_WIDTH / 2)
            vx = hitFraction * 5f
            vy = -6f
        }

        // Ball fell below screen — lose a life
        if (by - BALL_RADIUS > WORLD_HEIGHT) {
            val newLives = s.lives - 1
            if (newLives <= 0) {
                if (s.score > highScore) highScore = s.score
                gameState = s.copy(isGameOver = true, isPlaying = false)
                gameJob?.cancel()
            } else {
                gameState = s.copy(
                    lives = newLives,
                    ballX = BALL_START_X,
                    ballY = BALL_START_Y,
                    ballVX = INITIAL_VX,
                    ballVY = INITIAL_VY
                )
            }
            return
        }

        // Brick collisions — only one brick per tick
        var newBricks = s.bricks
        var newScore = s.score
        for (i in newBricks.indices) {
            val brick = newBricks[i]
            if (!brick.isAlive) continue

            val brickX = brickLeft(brick.col)
            val brickY = brickTop(brick.row)
            val nearX = bx.coerceIn(brickX, brickX + BRICK_WIDTH)
            val nearY = by.coerceIn(brickY, brickY + BRICK_HEIGHT)
            val dx = bx - nearX
            val dy = by - nearY

            if (dx * dx + dy * dy <= BALL_RADIUS * BALL_RADIUS) {
                newBricks = newBricks.mapIndexed { idx, b -> if (idx == i) b.copy(isAlive = false) else b }
                newScore += 10 * (BRICK_ROWS - brick.row)
                if (abs(dx) <= abs(dy)) vy = -vy else vx = -vx
                break
            }
        }

        // Win check
        if (newBricks.none { it.isAlive }) {
            if (newScore > highScore) highScore = newScore
            gameState = s.copy(
                ballX = bx, ballY = by, ballVX = vx, ballVY = vy,
                bricks = newBricks, score = newScore,
                isWon = true, isPlaying = false
            )
            gameJob?.cancel()
            return
        }

        gameState = s.copy(
            ballX = bx, ballY = by, ballVX = vx, ballVY = vy,
            bricks = newBricks, score = newScore
        )
    }

    private fun buildInitialState(): BrickBreakerGameState {
        val bricks = (0 until BRICK_ROWS).flatMap { row ->
            (0 until BRICK_COLS).map { col -> Brick(row = row, col = col) }
        }
        return BrickBreakerGameState(
            ballX = BALL_START_X,
            ballY = BALL_START_Y,
            ballVX = INITIAL_VX,
            ballVY = INITIAL_VY,
            paddleX = WORLD_WIDTH / 2,
            bricks = bricks,
            lives = INITIAL_LIVES
        )
    }

    override fun onCleared() {
        super.onCleared()
        gameJob?.cancel()
    }
}
