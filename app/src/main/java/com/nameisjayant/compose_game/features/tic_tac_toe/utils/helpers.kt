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

        val winResult = checkWinner(newBoard)
        val isDraw = winResult == null && newBoard.flatten().none { it == null }

        return state.copy(
            board = newBoard,
            currentPlayer = if (state.currentPlayer == Player.X) Player.O else Player.X,
            winner = winResult?.first,
            isDraw = isDraw,
            winningCells = winResult?.second ?: emptyList()
        )
    }

    private fun checkWinner(board: List<List<Player?>>): Pair<Player, List<Pair<Int, Int>>>? {
        val lines = listOf(
            listOf(0 to 0, 0 to 1, 0 to 2),
            listOf(1 to 0, 1 to 1, 1 to 2),
            listOf(2 to 0, 2 to 1, 2 to 2),
            listOf(0 to 0, 1 to 0, 2 to 0),
            listOf(0 to 1, 1 to 1, 2 to 1),
            listOf(0 to 2, 1 to 2, 2 to 2),
            listOf(0 to 0, 1 to 1, 2 to 2),
            listOf(0 to 2, 1 to 1, 2 to 0)
        )

        return lines.firstOrNull { line ->
            val cells = line.map { (r, c) -> board[r][c] }
            cells.all { it != null && it == cells.first() }
        }?.let { line ->
            Pair(board[line[0].first][line[0].second]!!, line)
        }
    }
}
