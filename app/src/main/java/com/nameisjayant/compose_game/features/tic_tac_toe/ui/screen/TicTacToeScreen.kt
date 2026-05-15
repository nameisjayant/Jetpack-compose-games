package com.nameisjayant.compose_game.features.tic_tac_toe.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import kotlinx.coroutines.delay
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nameisjayant.compose_game.features.tic_tac_toe.ui.components.TicTacToeBoard
import com.nameisjayant.compose_game.features.tic_tac_toe.ui.viewmodel.TicTacToeViewModel
import com.nameisjayant.compose_game.features.tic_tac_toe.utils.Player

private val ScreenBg = Color(0xFF0D0D1A)
private val BoardCardBg = Color(0xFF1A1A2E)
private val ScoreCardBg = Color(0xFF1E1E38)
private val XColor = Color(0xFFFF6B6B)
private val OColor = Color(0xFF4DD0E1)
private val MutedColor = Color(0xFF6B6B8A)
private val AccentButton = Color(0xFF6C63FF)

@Composable
fun TicTacToeScreen(
    modifier: Modifier = Modifier,
    viewModel: TicTacToeViewModel = viewModel()
) {
    val gameState = viewModel.gameState
    val isGameOver = gameState.winner != null || gameState.isDraw

    LaunchedEffect(isGameOver) {
        if (isGameOver) {
            delay(2000)
            viewModel.resetGame()
        }
    }

    val (statusText, statusColor) = when {
        gameState.winner == Player.X -> "Player X Wins!" to XColor
        gameState.winner == Player.O -> "Player O Wins!" to OColor
        gameState.isDraw -> "It's a Draw!" to Color(0xFFFFC107)
        gameState.currentPlayer == Player.X -> "Player X's Turn" to XColor.copy(alpha = 0.85f)
        else -> "Player O's Turn" to OColor.copy(alpha = 0.85f)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ScreenBg)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(52.dp))

        Text(
            text = "TIC TAC TOE",
            color = Color.White,
            fontSize = 26.sp,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 4.sp
        )

        Spacer(modifier = Modifier.height(36.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            PlayerScoreCard(
                symbol = "X",
                score = viewModel.xScore,
                color = XColor,
                isActive = !isGameOver && gameState.currentPlayer == Player.X
            )

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "DRAWS",
                    color = MutedColor,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 1.5.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${viewModel.drawScore}",
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            PlayerScoreCard(
                symbol = "O",
                score = viewModel.oScore,
                color = OColor,
                isActive = !isGameOver && gameState.currentPlayer == Player.O
            )
        }

        Spacer(modifier = Modifier.height(36.dp))

        Box(
            modifier = Modifier
                .background(BoardCardBg, RoundedCornerShape(20.dp))
                .padding(8.dp)
        ) {
            TicTacToeBoard(state = gameState, onCellClick = viewModel::makeMove)
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = statusText,
            color = statusColor,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(28.dp))

        Button(
            onClick = viewModel::resetGame,
            colors = ButtonDefaults.buttonColors(containerColor = AccentButton),
            shape = RoundedCornerShape(50),
            contentPadding = PaddingValues(horizontal = 40.dp, vertical = 14.dp),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
        ) {
            Text(
                text = "NEW GAME",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 2.sp,
                color = Color.White
            )
        }
    }
}

@Composable
private fun PlayerScoreCard(
    symbol: String,
    score: Int,
    color: Color,
    isActive: Boolean
) {
    val bgColor = if (isActive) color.copy(alpha = 0.15f) else ScoreCardBg

    Box(
        modifier = Modifier
            .width(110.dp)
            .background(bgColor, RoundedCornerShape(16.dp))
            .then(
                if (isActive) Modifier.border(2.dp, color, RoundedCornerShape(16.dp))
                else Modifier
            )
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = symbol,
                color = color,
                fontSize = 30.sp,
                fontWeight = FontWeight.ExtraBold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "$score",
                color = Color.White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
