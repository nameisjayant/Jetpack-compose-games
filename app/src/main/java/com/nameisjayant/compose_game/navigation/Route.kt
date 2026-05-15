package com.nameisjayant.compose_game.navigation

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object GamesList : Route

    @Serializable
    data object TicTacToe : Route

    @Serializable
    data object Snake : Route

    @Serializable
    data object FlappyBird : Route
}
