package com.nameisjayant.compose_game.navigation

import kotlinx.serialization.Serializable

sealed interface Route {

    @Serializable
    data object GamesList : Route

    @Serializable
    data object TicTacToe : Route
}
