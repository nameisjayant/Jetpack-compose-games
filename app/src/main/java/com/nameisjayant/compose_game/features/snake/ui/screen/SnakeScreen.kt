package com.nameisjayant.compose_game.features.snake.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nameisjayant.compose_game.features.snake.ui.components.DirectionControls
import com.nameisjayant.compose_game.features.snake.ui.components.SnakeBoard
import com.nameisjayant.compose_game.features.snake.ui.components.SnakeScoreHeader
import com.nameisjayant.compose_game.features.snake.ui.viewmodel.SnakeViewModel
import com.nameisjayant.compose_game.features.tic_tac_toe.ui.components.BackButton
import com.nameisjayant.compose_game.ui.theme.AccentPurple
import com.nameisjayant.compose_game.ui.theme.DarkBackground
import com.nameisjayant.compose_game.ui.theme.PlayerXColor

@Composable
fun SnakeScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit = {},
    viewModel: SnakeViewModel = viewModel()
) {
    val gameState = viewModel.gameState

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(52.dp))

        Box(modifier = Modifier.fillMaxWidth()) {
            BackButton(
                onClick = { onNavigateBack() },
                modifier = Modifier.align(Alignment.CenterStart)
            )
            Text(
                text = "SNAKE",
                color = Color.White,
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 4.sp,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        SnakeScoreHeader(
            score = gameState.score,
            highScore = viewModel.highScore
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(modifier = Modifier.fillMaxWidth()) {
            SnakeBoard(gameState = gameState)

            if (!gameState.isPlaying) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(Color.Black.copy(alpha = 0.75f), RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        if (gameState.isGameOver) {
                            Text(
                                text = "GAME OVER",
                                color = PlayerXColor,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.ExtraBold,
                                letterSpacing = 2.sp
                            )
                            Text(
                                text = "Score: ${gameState.score}",
                                color = Color.White,
                                fontSize = 15.sp
                            )
                        }
                        Button(
                            onClick = viewModel::startGame,
                            colors = ButtonDefaults.buttonColors(containerColor = AccentPurple),
                            shape = RoundedCornerShape(50),
                            contentPadding = PaddingValues(horizontal = 36.dp, vertical = 12.dp)
                        ) {
                            Text(
                                text = if (gameState.isGameOver) "PLAY AGAIN" else "START GAME",
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 2.sp,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        DirectionControls(onDirection = viewModel::changeDirection)

        Spacer(modifier = Modifier.height(24.dp))
    }
}
