package com.nameisjayant.compose_game.features.games_list.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nameisjayant.compose_game.ui.theme.AccentPurple
import com.nameisjayant.compose_game.ui.theme.DarkBackground
import com.nameisjayant.compose_game.ui.theme.DarkBorder
import com.nameisjayant.compose_game.ui.theme.DarkSurface
import com.nameisjayant.compose_game.ui.theme.MutedText
import com.nameisjayant.compose_game.ui.theme.FlappyBirdColor
import com.nameisjayant.compose_game.ui.theme.SnakeHeadColor

private data class GameItem(
    val title: String,
    val description: String,
    val emoji: String,
    val accentColor: Color,
    val onClick: () -> Unit
)

@Composable
fun GamesListScreen(
    modifier: Modifier = Modifier,
    onTicTacToeClick: () -> Unit,
    onSnakeClick: () -> Unit,
    onFlappyBirdClick: () -> Unit
) {
    val games = listOf(
        GameItem(
            title = "Tic Tac Toe",
            description = "2 players · Classic",
            emoji = "✕ ○",
            accentColor = AccentPurple,
            onClick = onTicTacToeClick
        ),
        GameItem(
            title = "Snake",
            description = "1 player · Arcade",
            emoji = "🐍",
            accentColor = SnakeHeadColor,
            onClick = onSnakeClick
        ),
        GameItem(
            title = "Flappy Bird",
            description = "1 player · Tap to fly",
            emoji = "🐦",
            accentColor = FlappyBirdColor,
            onClick = onFlappyBirdClick
        )
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground)
            .statusBarsPadding()
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = "GAMES",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            letterSpacing = 4.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Choose a game to play",
            color = MutedText,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        LazyColumn {
            items(games) { game ->
                GameCard(game)
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
private fun GameCard(game: GameItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(DarkSurface)
            .border(1.dp, DarkBorder, RoundedCornerShape(20.dp))
            .clickable { game.onClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .background(game.accentColor.copy(alpha = 0.15f), RoundedCornerShape(14.dp))
                .border(1.dp, game.accentColor.copy(alpha = 0.30f), RoundedCornerShape(14.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = game.emoji,
                fontSize = 18.sp,
                color = game.accentColor,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = game.title,
                color = Color.White,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = game.description,
                color = MutedText,
                fontSize = 13.sp
            )
        }

        Text(
            text = "›",
            color = MutedText,
            fontSize = 26.sp,
            fontWeight = FontWeight.Light
        )
    }
}
