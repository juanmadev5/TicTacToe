package com.jmdev.app.tictactoe.ui.theme.screens

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jmdev.app.tictactoe.GameViewModel
import com.jmdev.app.tictactoe.R
import com.jmdev.app.tictactoe.ui.theme.TicTacToeTheme
import com.jmdev.app.tictactoe.ui.theme.components.AppBar

@ExperimentalMaterial3Api
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TicTacToeApp() {
    val context = LocalContext.current
    val gameViewModel: GameViewModel = viewModel()
    val board = gameViewModel.board.value
    val result = gameViewModel.result.value
    val showDialog = remember { mutableStateOf(false) }
    TicTacToeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Scaffold(
                content = {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(16.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AppBar(showDialog)
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(500.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Column(
                                verticalArrangement = Arrangement.Center
                            ) {
                                for (row in board.indices) {
                                    Row {
                                        for (col in board[row].indices) {
                                            Box(
                                                modifier = Modifier
                                                    .size(80.dp)
                                                    .padding(4.dp)
                                                    .clip(RoundedCornerShape(14.dp))
                                                    .clickable {
                                                        gameViewModel.makeMove(row, col)
                                                    },
                                                contentAlignment = Alignment.Center
                                            ) {
                                                Card(
                                                    modifier = Modifier
                                                        .fillMaxSize()
                                                        .clip(
                                                            RoundedCornerShape(16.dp)
                                                        )
                                                ) {
                                                    Box(
                                                        modifier = Modifier.fillMaxSize(),
                                                        contentAlignment = Alignment.Center
                                                    ) {
                                                        Text(
                                                            text = board[row][col],
                                                            style = MaterialTheme.typography.bodyMedium,
                                                            color = MaterialTheme.colorScheme.onSurface
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Turno: ${gameViewModel.currentPlayer.value}",
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        if (result.isNotEmpty()) {
                            Text(
                                text = when (result) {
                                    "X" -> "¡X ganó!"
                                    "O" -> "¡O ganó!"
                                    else -> "Empate"
                                },
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(onClick = { gameViewModel.resetGame() }) {
                                Text(text = "Reiniciar")
                            }
                        }
                        if (showDialog.value) {
                            AlertDialog(
                                onDismissRequest = { showDialog.value = false },
                                icon = {
                                    androidx.compose.material3.Icon(
                                        imageVector = Icons.Filled.Info,
                                        contentDescription = "info"
                                    )
                                },
                                title = { Text(text = "Información de la aplicación") },
                                text = {
                                    Column(Modifier.wrapContentHeight()) {
                                        Row(
                                            Modifier
                                                .fillMaxWidth()
                                                .wrapContentHeight()
                                        ) {
                                            Icon(
                                                imageVector = Icons.Filled.Build,
                                                contentDescription = "app ver"
                                            )
                                            Spacer(modifier = Modifier.padding(start = 12.dp))
                                            Text(text = stringResource(id = R.string.app_name) + "  v1.0")
                                        }
                                        Spacer(modifier = Modifier.padding(top = 12.dp))
                                        Row(
                                            Modifier
                                                .fillMaxWidth()
                                                .wrapContentHeight()
                                        ) {
                                            Icon(
                                                imageVector = Icons.Filled.Person,
                                                contentDescription = "developer"
                                            )
                                            Spacer(modifier = Modifier.padding(start = 12.dp))
                                            Text(text = "Desarrollador: JMDev")
                                        }
                                        Spacer(modifier = Modifier.padding(top = 12.dp))
                                        Row(
                                            Modifier
                                                .fillMaxWidth()
                                                .wrapContentHeight()
                                        ) {
                                            Icon(
                                                painter = painterResource(id = R.drawable.baseline_insert_link_24),
                                                contentDescription = "git hub"
                                            )
                                            Spacer(modifier = Modifier.padding(start = 12.dp))
                                            Text(
                                                text = "Proyecto en GitHub",
                                                modifier = Modifier.clickable {
                                                    val url = "https://github.com/tu-proyecto"
                                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                                    startActivity(context, intent, null)
                                                },
                                                textDecoration = TextDecoration.Underline
                                            )
                                        }
                                    }

                                },
                                confirmButton = {},
                                dismissButton = {}
                            )
                        }
                    }
                }
            )
        }
    }
}