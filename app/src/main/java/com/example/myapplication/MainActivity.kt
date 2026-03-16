package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TicTacToeApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

private enum class Player(val symbol: String) {
    X("X"),
    O("O");
    fun next(): Player = if (this == X) O else X
}

@Composable
private fun TicTacToeApp(modifier: Modifier = Modifier) {
    val board = remember {
        mutableStateListOf<Player?>(null, null, null, null, null, null, null, null, null)
    }
    var currentPlayer by remember { mutableStateOf(Player.X) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(horizontal = 20.dp, vertical = 24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Player ${currentPlayer.symbol}'s turn",
            fontSize = 24.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFF1F1F1F),
            textAlign = TextAlign.Center
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f),
            contentPadding = PaddingValues(all = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            userScrollEnabled = false,
        ) {
            items(board.size) { index ->
                GameCell(
                    player = board[index],
                    onClick = {
                        if (board[index] == null) {
                            board[index] = currentPlayer
                            currentPlayer = currentPlayer.next()
                        }
                    }
                )
            }
        }
        Button(
            onClick = {
                board.indices.forEach { board[it] = null }
                currentPlayer = Player.X
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(84.dp),
            shape = RectangleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(red = 0, green = 254, blue = 0),
                contentColor = Color(0xFF1F1F1F)
            )
        ) {
            Text(
                text = "PLAY !!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
private fun GameCell(
    player: Player?,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .background(Color(red = 208, green = 209, blue = 210))
            .clickable(
                enabled = player == null,
                onClick = onClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = player?.symbol.orEmpty(),
            color = Color(0xFF1F1F1F),
            fontSize = 44.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun TicTacToeAppPreview() {
    MyApplicationTheme {
        TicTacToeApp()
    }
}
