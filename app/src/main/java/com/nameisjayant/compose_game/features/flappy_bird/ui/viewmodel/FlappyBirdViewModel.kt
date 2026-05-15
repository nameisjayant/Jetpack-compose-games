package com.nameisjayant.compose_game.features.flappy_bird.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nameisjayant.compose_game.features.flappy_bird.utils.FlappyBirdGameState
import com.nameisjayant.compose_game.features.flappy_bird.utils.FlappyBirdPipe
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class FlappyBirdViewModel : ViewModel() {

    companion object {
        const val WORLD_WIDTH = 400f
        const val WORLD_HEIGHT = 600f
        const val BIRD_X = 80f
        const val BIRD_RADIUS = 14f
        const val PIPE_WIDTH = 58f
        const val PIPE_GAP = 150f
        const val GROUND_Y = 555f
        private const val GRAVITY = 0.55f
        private const val FLAP_VELOCITY = -11f
        private const val MAX_FALL_VELOCITY = 14f
        private const val PIPE_SPEED = 2.8f
        private const val PIPE_SPACING = 210f
        private const val GAP_CENTER_MIN = 160f
        private const val GAP_CENTER_MAX = GROUND_Y - 160f
        private const val GAME_TICK_MS = 16L
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
            while (gameState.isPlaying && !gameState.isGameOver) {
                delay(GAME_TICK_MS)
                tick()
            }
        }
    }

    fun flap() {
        if (gameState.isPlaying && !gameState.isGameOver) {
            gameState = gameState.copy(birdVelocity = FLAP_VELOCITY)
        }
    }

    private fun tick() {
        val state = gameState

        val newVelocity = (state.birdVelocity + GRAVITY).coerceAtMost(MAX_FALL_VELOCITY)
        val newBirdY = state.birdY + newVelocity

        var newScore = state.score
        val movedPipes = state.pipes.map { pipe ->
            val moved = pipe.copy(x = pipe.x - PIPE_SPEED)
            if (!moved.passed && BIRD_X > moved.x + PIPE_WIDTH) {
                newScore++
                moved.copy(passed = true)
            } else moved
        }.filter { it.x + PIPE_WIDTH > 0f }

        val pipesWithNew = if (movedPipes.isEmpty() || movedPipes.last().x < WORLD_WIDTH - PIPE_SPACING) {
            movedPipes + FlappyBirdPipe(
                x = WORLD_WIDTH,
                gapCenterY = Random.nextFloat() * (GAP_CENTER_MAX - GAP_CENTER_MIN) + GAP_CENTER_MIN
            )
        } else movedPipes

        val hitGround = newBirdY + BIRD_RADIUS >= GROUND_Y
        val hitCeiling = newBirdY - BIRD_RADIUS <= 0f
        val hitPipe = pipesWithNew.any { pipe ->
            val hOverlap = BIRD_X + BIRD_RADIUS > pipe.x && BIRD_X - BIRD_RADIUS < pipe.x + PIPE_WIDTH
            if (!hOverlap) return@any false
            newBirdY < pipe.gapCenterY - PIPE_GAP / 2 || newBirdY > pipe.gapCenterY + PIPE_GAP / 2
        }

        if (hitGround || hitCeiling || hitPipe) {
            if (newScore > highScore) highScore = newScore
            gameState = state.copy(
                birdY = newBirdY.coerceAtMost(GROUND_Y - BIRD_RADIUS),
                birdVelocity = newVelocity,
                pipes = pipesWithNew,
                score = newScore,
                isGameOver = true,
                isPlaying = false
            )
            gameJob?.cancel()
            return
        }

        gameState = state.copy(
            birdY = newBirdY,
            birdVelocity = newVelocity,
            pipes = pipesWithNew,
            score = newScore
        )
    }

    private fun buildInitialState() = FlappyBirdGameState(birdY = WORLD_HEIGHT / 2f)

    override fun onCleared() {
        super.onCleared()
        gameJob?.cancel()
    }
}
