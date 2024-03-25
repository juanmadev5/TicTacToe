package com.jmdev.app.tictactoe.ui.theme.screens

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.viewmodel.compose.viewModel
import com.jmdev.app.tictactoe.GameViewModel
import com.jmdev.app.tictactoe.R
import com.jmdev.app.tictactoe.ui.theme.TicTacToeTheme
import com.jmdev.app.tictactoe.ui.theme.components.AppBar

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
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                AppBar(showDialog)
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(dimensionResource(id = R.dimen.mid_padding))
                        .align(Alignment.Center),
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
                                            .size(dimensionResource(id = R.dimen.box_size))
                                            .padding(dimensionResource(id = R.dimen.small_padding))
                                            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.default_corner_shape)))
                                            .clickable {
                                                gameViewModel.makeMove(row, col)
                                            },
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Card(
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .clip(
                                                    RoundedCornerShape(dimensionResource(id = R.dimen.default_padding))
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

                Text(
                    text = stringResource(R.string.turn) + gameViewModel.currentPlayer.value,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = dimensionResource(id = R.dimen.default_padding))
                )
                if (result.isNotEmpty()) {
                    Text(
                        text = when (result) {
                            "X" -> stringResource(R.string.x_win)
                            "O" -> stringResource(R.string.o_win)
                            else -> stringResource(R.string.tie)
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = dimensionResource(id = R.dimen.large_padding))
                    )
                    Button(
                        onClick = { gameViewModel.resetGame() },
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                    ) {
                        Text(text = stringResource(R.string.restart))
                    }
                }
                if (showDialog.value) {
                    AlertDialog(
                        onDismissRequest = { showDialog.value = false },
                        icon = {
                            Icon(
                                imageVector = Icons.Filled.Info,
                                contentDescription = stringResource(R.string.info_alt)
                            )
                        },
                        title = { Text(text = stringResource(R.string.app_info)) },
                        text = {
                            Column(Modifier.wrapContentHeight()) {
                                Spacer(modifier = Modifier.padding(top = dimensionResource(id = R.dimen.special_padding)))
                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight()
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Build,
                                        contentDescription = stringResource(R.string.app_ver)
                                    )
                                    Spacer(modifier = Modifier.padding(start = dimensionResource(id = R.dimen.special_padding)))
                                    Text(text = stringResource(id = R.string.app_name) + "  v1.0")
                                }
                                Spacer(modifier = Modifier.padding(top = dimensionResource(id = R.dimen.special_padding)))
                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight()
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Person,
                                        contentDescription = stringResource(R.string.developer)
                                    )
                                    Spacer(modifier = Modifier.padding(start = dimensionResource(id = R.dimen.special_padding)))
                                    Text(text = stringResource(R.string.dev_name))
                                }
                                Spacer(modifier = Modifier.padding(top = dimensionResource(id = R.dimen.special_padding)))
                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight()
                                ) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.baseline_insert_link_24),
                                        contentDescription = stringResource(R.string.git_hub)
                                    )
                                    Spacer(modifier = Modifier.padding(start = dimensionResource(id = R.dimen.special_padding)))
                                    Text(
                                        text = stringResource(R.string.proyect_in_github),
                                        modifier = Modifier.clickable {
                                            val url =
                                                "https://github.com/juanmadev5/TicTacToe"
                                            val intent =
                                                Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                            startActivity(context, intent, null)
                                        },
                                        textDecoration = TextDecoration.Underline
                                    )
                                }
                                Spacer(modifier = Modifier.padding(top = dimensionResource(id = R.dimen.special_padding)))
                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                        .wrapContentHeight()
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Favorite,
                                        contentDescription = stringResource(R.string.love_from_paraguay)
                                    )
                                    Spacer(modifier = Modifier.padding(start = dimensionResource(id = R.dimen.special_padding)))
                                    Text(text = stringResource(R.string.with_heart))
                                }
                            }

                        },
                        confirmButton = {},
                        dismissButton = {}
                    )
                }
            }
        }
    }
}