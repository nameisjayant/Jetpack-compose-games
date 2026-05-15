package com.nameisjayant.compose_game.features.flappy_bird.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nameisjayant.compose_game.features.flappy_bird.ui.components.FlappyBirdCanvas
import com.nameisjayant.compose_game.features.flappy_bird.ui.viewmodel.FlappyBirdViewModel
import com.nameisjayant.compose_game.features.tic_tac_toe.ui.components.BackButton
import com.nameisjayant.compose_game.ui.theme.AccentPurple
import com.nameisjayant.compose_game.ui.theme.DarkBackground
import com.nameisjayant.compose_game.ui.theme.FlappyBirdColor
import com.nameisjayant.compose_game.ui.theme.MutedText
import com.nameisjayant.compose_game.ui.theme.PlayerXColor

@Composable
fun FlappyBirdScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit = {},
    viewModel: FlappyBirdViewModel = viewModel()
) {
    val gameState = viewModel.gameState

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(52.dp))

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
                text = "FLAPPY BIRD",
                color = Color.White,
                fontSize = 22.sp,
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
                    color = FlappyBirdColor,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 20.dp)
                .clip(RoundedCornerShape(16.dp))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { viewModel.flap() }
        ) {
            FlappyBirdCanvas(
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
            } else {
                Text(
                    text = "${gameState.score}",
                    color = Color.White,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = if (gameState.isPlaying) "TAP ANYWHERE TO FLAP" else "",
            color = Color.White.copy(alpha = 0.4f),
            fontSize = 12.sp,
            letterSpacing = 1.sp
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}
