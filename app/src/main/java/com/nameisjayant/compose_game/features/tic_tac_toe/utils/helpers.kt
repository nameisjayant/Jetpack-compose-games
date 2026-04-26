package com.nameisjayant.compose_game.features.tic_tac_toe.utils


object TicTacToeHelpers {

    fun createEmptyBoard() = List(3) { List<Player?>(3) { null } }

    fun makeMove(state: GameState, row: Int, col: Int): GameState {
        if (state.board[row][col] != null || state.winner != null) return state

        val newBoard = state.board.mapIndexed { r, rowList ->
            rowList.mapIndexed { c, cell ->
                if (r == row && c == col) state.currentPlayer else cell
            }
        }

        val winner = checkWinner(newBoard)
        val isDraw = winner == null && newBoard.flatten().none { it == null }

        return state.copy(
            board = newBoard,
            currentPlayer = if (state.currentPlayer == Player.X) Player.O else Player.X,
            winner = winner,
            isDraw = isDraw
        )
    }

    private fun checkWinner(board: List<List<Player?>>): Player? {
        val lines = listOf(
            // Rows
            board[0], board[1], board[2],
            // Columns
            listOf(board[0][0], board[1][0], board[2][0]),
            listOf(board[0][1], board[1][1], board[2][1]),
            listOf(board[0][2], board[1][2], board[2][2]),
            // Diagonals
            listOf(board[0][0], board[1][1], board[2][2]),
            listOf(board[0][2], board[1][1], board[2][0])
        )

        return lines.firstOrNull { line ->
            line.all { it != null && it == line.first() }
        }?.first()
    }
}