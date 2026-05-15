package com.nameisjayant.compose_game.navigation

import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nameisjayant.compose_game.features.games_list.ui.screen.GamesListScreen
import com.nameisjayant.compose_game.features.snake.ui.screen.SnakeScreen
import com.nameisjayant.compose_game.features.tic_tac_toe.ui.screen.TicTacToeScreen

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.GamesList,
        modifier = modifier,
        enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) },
        popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }) },
        popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) }
    ) {
        composable<Route.GamesList> {
            GamesListScreen(
                onTicTacToeClick = {
                    val alreadyInStack = navController.currentBackStack.value
                        .any { it.destination.route == Route.TicTacToe::class.qualifiedName }
                    if (!alreadyInStack) {
                        navController.navigate(Route.TicTacToe)
                    }
                },
                onSnakeClick = {
                    val alreadyInStack = navController.currentBackStack.value
                        .any { it.destination.route == Route.Snake::class.qualifiedName }
                    if (!alreadyInStack) {
                        navController.navigate(Route.Snake)
                    }
                }
            )
        }

        composable<Route.TicTacToe> {
            TicTacToeScreen(onNavigateBack = { navController.navigateUp() })
        }

        composable<Route.Snake> {
            SnakeScreen(onNavigateBack = { navController.navigateUp() })
        }
    }
}
