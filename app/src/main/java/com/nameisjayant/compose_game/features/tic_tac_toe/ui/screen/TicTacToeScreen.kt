package com.nameisjayant.compose_game.features.tic_tac_toe.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nameisjayant.compose_game.features.tic_tac_toe.ui.components.BackButton
import com.nameisjayant.compose_game.features.tic_tac_toe.ui.components.GameStatusText
import com.nameisjayant.compose_game.features.tic_tac_toe.ui.components.NewGameButton
import com.nameisjayant.compose_game.features.tic_tac_toe.ui.components.ScoreBoard
import com.nameisjayant.compose_game.features.tic_tac_toe.ui.components.TicTacToeBoard
import com.nameisjayant.compose_game.features.tic_tac_toe.ui.viewmodel.TicTacToeViewModel
import com.nameisjayant.compose_game.features.tic_tac_toe.utils.Player
import com.nameisjayant.compose_game.ui.theme.DarkBackground
import com.nameisjayant.compose_game.ui.theme.DarkSurface
import com.nameisjayant.compose_game.ui.theme.DrawAmber
import com.nameisjayant.compose_game.ui.theme.PlayerOColor
import com.nameisjayant.compose_game.ui.theme.PlayerXColor
import kotlinx.coroutines.delay

@Composable
fun TicTacToeScreen(
    modifier: Modifier = Modifier,
    onNavigateBack: () -> Unit = {},
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
        gameState.winner == Player.X -> "Player X Wins!" to PlayerXColor
        gameState.winner == Player.O -> "Player O Wins!" to PlayerOColor
        gameState.isDraw -> "It's a Draw!" to DrawAmber
        gameState.currentPlayer == Player.X -> "Player X's Turn" to PlayerXColor.copy(alpha = 0.85f)
        else -> "Player O's Turn" to PlayerOColor.copy(alpha = 0.85f)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground)
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(52.dp))

        Box(modifier = Modifier.fillMaxWidth()) {
            BackButton(
                onClick = onNavigateBack,
                modifier = Modifier.align(Alignment.CenterStart)
            )
            Text(
                text = "TIC TAC TOE",
                color = Color.White,
                fontSize = 26.sp,
                fontWeight = FontWeight.ExtraBold,
                letterSpacing = 4.sp,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.height(36.dp))

        ScoreBoard(
            xScore = viewModel.xScore,
            oScore = viewModel.oScore,
            drawScore = viewModel.drawScore,
            currentPlayer = gameState.currentPlayer,
            isGameOver = isGameOver
        )

        Spacer(modifier = Modifier.height(36.dp))

        Box(
            modifier = Modifier
                .background(DarkSurface, RoundedCornerShape(20.dp))
                .padding(8.dp)
        ) {
            TicTacToeBoard(state = gameState, onCellClick = viewModel::makeMove)
        }

        Spacer(modifier = Modifier.height(32.dp))

        GameStatusText(text = statusText, color = statusColor)

        Spacer(modifier = Modifier.height(28.dp))

        NewGameButton(onClick = viewModel::resetGame)
    }
}
