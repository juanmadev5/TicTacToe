package com.jmdev.app.tictactoe

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    private val boardSize = 3

    val board = mutableStateOf(Array(boardSize) { Array(boardSize) { "" } })

    val currentPlayer = mutableStateOf("X")

    val result = mutableStateOf("")

    fun makeMove(row: Int, col: Int) {
        if (board.value[row][col].isEmpty() && result.value.isEmpty()) {
            board.value[row][col] = currentPlayer.value
            currentPlayer.value = if (currentPlayer.value == "X") "O" else "X"
            checkWinner()
        }
    }

    private fun checkWinner() {
        val lines = arrayOf(
            arrayOf(board.value[0][0], board.value[0][1], board.value[0][2]),
            arrayOf(board.value[1][0], board.value[1][1], board.value[1][2]),
            arrayOf(board.value[2][0], board.value[2][1], board.value[2][2]),
            arrayOf(board.value[0][0], board.value[1][0], board.value[2][0]),
            arrayOf(board.value[0][1], board.value[1][1], board.value[2][1]),
            arrayOf(board.value[0][2], board.value[1][2], board.value[2][2]),
            arrayOf(board.value[0][0], board.value[1][1], board.value[2][2]),
            arrayOf(board.value[0][2], board.value[1][1], board.value[2][0])
        )

        for (line in lines) {
            if (line[0].isNotEmpty() && line[0] == line[1] && line[1] == line[2]) {
                result.value = line[0]
                return
            }
        }

        if (board.value.all { row -> row.all { cell -> cell.isNotEmpty() } }) {
            result.value = "Empate"
        }
    }

    fun resetGame() {
        board.value = Array(boardSize) { Array(boardSize) { "" } }
        currentPlayer.value = "X"
        result.value = ""
    }
}