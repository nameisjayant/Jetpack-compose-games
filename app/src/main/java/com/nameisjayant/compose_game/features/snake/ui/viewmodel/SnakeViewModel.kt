package com.nameisjayant.compose_game.features.snake.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nameisjayant.compose_game.features.snake.utils.Direction
import com.nameisjayant.compose_game.features.snake.utils.SnakeGameState
import com.nameisjayant.compose_game.features.snake.utils.SnakePosition
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SnakeViewModel : ViewModel() {

    companion object {
        const val GRID_SIZE = 20
        private const val GAME_SPEED_MS = 150L
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
                delay(GAME_SPEED_MS)
                tick()
            }
        }
    }

    fun changeDirection(newDirection: Direction) {
        val current = gameState.direction
        val isReverse = (current == Direction.UP && newDirection == Direction.DOWN) ||
                (current == Direction.DOWN && newDirection == Direction.UP) ||
                (current == Direction.LEFT && newDirection == Direction.RIGHT) ||
                (current == Direction.RIGHT && newDirection == Direction.LEFT)
        if (!isReverse) gameState = gameState.copy(direction = newDirection)
    }

    private fun tick() {
        val state = gameState
        val head = state.snake.first()

        val newHead = when (state.direction) {
            Direction.UP -> SnakePosition(head.x, head.y - 1)
            Direction.DOWN -> SnakePosition(head.x, head.y + 1)
            Direction.LEFT -> SnakePosition(head.x - 1, head.y)
            Direction.RIGHT -> SnakePosition(head.x + 1, head.y)
        }

        if (newHead.x !in 0 until GRID_SIZE || newHead.y !in 0 until GRID_SIZE || newHead in state.snake) {
            if (state.score > highScore) highScore = state.score
            gameState = state.copy(isGameOver = true, isPlaying = false)
            gameJob?.cancel()
            return
        }

        val ateFood = newHead == state.food
        val newSnake = if (ateFood) listOf(newHead) + state.snake
                       else listOf(newHead) + state.snake.dropLast(1)

        gameState = state.copy(
            snake = newSnake,
            food = if (ateFood) generateFood(newSnake) else state.food,
            score = if (ateFood) state.score + 1 else state.score
        )
    }

    private fun buildInitialState(): SnakeGameState {
        val snake = listOf(
            SnakePosition(10, 10),
            SnakePosition(10, 11),
            SnakePosition(10, 12)
        )
        return SnakeGameState(
            snake = snake,
            food = generateFood(snake),
            direction = Direction.UP
        )
    }

    private fun generateFood(snake: List<SnakePosition>): SnakePosition {
        val snakeSet = snake.toHashSet()
        val available = (0 until GRID_SIZE).flatMap { x ->
            (0 until GRID_SIZE).map { y -> SnakePosition(x, y) }
        }.filter { it !in snakeSet }
        return if (available.isEmpty()) snake.first() else available.random()
    }

    override fun onCleared() {
        super.onCleared()
        gameJob?.cancel()
    }
}
