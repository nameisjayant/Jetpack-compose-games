package com.nameisjayant.compose_game.features.brick_breaker.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nameisjayant.compose_game.features.brick_breaker.ui.components.BrickBreakerCanvas
import com.nameisjayant.compose_game.features.brick_breaker.ui.viewmodel.BrickBreakerViewModel
import com.nameisjayant.compose_game.features.tic_tac_toe.ui.components.BackButton
import com.nameisjayant.compose_game.ui.theme.AccentPurple
import com.nameisjayant.compose_game.ui.theme.DarkBackground
import com.nameisjayant.compose_game.ui.theme.MutedText
import com.nameisjayant.compose_game.ui.theme.PlayerXColor
import com.nameisjayant.compose_game.ui.theme.SnakeHeadColor

@Composable
fun BrickBreakerScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit = {},
    viewModel: BrickBreakerViewModel = viewModel()
) {
    val gameState = viewModel.gameState

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(52.dp))

        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            BackButton(
                onClick = onNavigateBack,
                modifier = Modifier.align(Alignment.CenterStart)
            )
            Text(
                text = "BRICK BREAKER",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 3.sp,
                modifier = Modifier.align(Alignment.Center)
            )
            Column(
                modifier = Modifier.align(Alignment.CenterEnd),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "BEST", color = MutedText, fontSize = 10.sp, letterSpacing = 1.sp)
                Text(
                    text = "${viewModel.highScore}",
                    color = SnakeHeadColor,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Score + lives row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "SCORE  ", color = MutedText, fontSize = 12.sp, letterSpacing = 1.sp)
                Text(
                    text = "${gameState.score}",
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
            Row {
                repeat(3) { i ->
                    Text(
                        text = "♥",
                        color = if (i < gameState.lives) PlayerXColor else PlayerXColor.copy(alpha = 0.2f),
                        fontSize = 18.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Game canvas
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 20.dp)
                .clip(RoundedCornerShape(16.dp))
                .pointerInput(Unit) {
                    awaitPointerEventScope {
                        while (true) {
                            val event = awaitPointerEvent()
                            event.changes.firstOrNull()?.let { change ->
                                val worldX = change.position.x / size.width.toFloat() *
                                        BrickBreakerViewModel.WORLD_WIDTH
                                viewModel.movePaddleTo(worldX)
                                change.consume()
                            }
                        }
                    }
                }
        ) {
            BrickBreakerCanvas(
                gameState = gameState,
                modifier = Modifier.fillMaxSize()
            )

            if (!gameState.isPlaying) {
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(Color.Black.copy(alpha = 0.65f)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        when {
                            gameState.isWon -> {
                                Text(
                                    text = "YOU WIN! 🎉",
                                    color = SnakeHeadColor,
                                    fontSize = 26.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    letterSpacing = 2.sp
                                )
                                Text(
                                    text = "Score: ${gameState.score}",
                                    color = Color.White,
                                    fontSize = 15.sp
                                )
                            }
                            gameState.isGameOver -> {
                                Text(
                                    text = "GAME OVER",
                                    color = PlayerXColor,
                                    fontSize = 26.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    letterSpacing = 2.sp
                                )
                                Text(
                                    text = "Score: ${gameState.score}",
                                    color = Color.White,
                                    fontSize = 15.sp
                                )
                            }
                        }
                        Button(
                            onClick = viewModel::startGame,
                            colors = ButtonDefaults.buttonColors(containerColor = AccentPurple),
                            shape = RoundedCornerShape(50),
                            contentPadding = PaddingValues(horizontal = 36.dp, vertical = 12.dp)
                        ) {
                            Text(
                                text = when {
                                    gameState.isWon || gameState.isGameOver -> "PLAY AGAIN"
                                    else -> "START GAME"
                                },
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 2.sp,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = if (gameState.isPlaying) "DRAG TO MOVE PADDLE" else "",
            color = Color.White.copy(alpha = 0.4f),
            fontSize = 12.sp,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}
