package com.nameisjayant.compose_game.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nameisjayant.compose_game.features.games_list.ui.screen.GamesListScreen
import com.nameisjayant.compose_game.features.tic_tac_toe.ui.screen.TicTacToeScreen

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.GamesList,
        modifier = modifier
    ) {
        composable<Route.GamesList> {
            GamesListScreen(
                onTicTacToeClick = { navController.navigate(Route.TicTacToe) }
            )
        }

        composable<Route.TicTacToe> {
            TicTacToeScreen()
        }
    }
}
