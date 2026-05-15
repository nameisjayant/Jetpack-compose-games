package com.nameisjayant.compose_game

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.nameisjayant.compose_game.navigation.AppNavigation
import com.nameisjayant.compose_game.ui.theme.JetpackComposeGameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetpackComposeGameTheme {
                AppNavigation(modifier = Modifier.fillMaxSize())
            }
        }
    }
}
