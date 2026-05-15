package com.nameisjayant.compose_game.features.tic_tac_toe.ui.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.nameisjayant.compose_game.ui.theme.AccentPurple
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NewGameButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(containerColor = AccentPurple),
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
