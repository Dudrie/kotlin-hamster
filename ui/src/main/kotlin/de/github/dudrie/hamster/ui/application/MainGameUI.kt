package de.github.dudrie.hamster.ui.application

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.ui.state.GameViewModel

internal val GameViewModelLocal = compositionLocalOf<GameViewModel> { error("No GameViewModel is provided.") }

@Composable
fun MainGameUI() {
    CompositionLocalProvider(GameViewModelLocal provides GameViewModel.model) {
        Scaffold(
            topBar = {
                TopAppBar(elevation = 8.dp) {
                    Text(
                        text = "CONTROLS",
                        color = MaterialTheme.colors.onPrimary,
                        style = MaterialTheme.typography.h4
                    )
                }
            }
        ) {
            Row(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier.padding(8.dp).weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    GameGridLayout()
                }

                Box(
                    modifier = Modifier.fillMaxHeight().width(300.dp).background(Color.LightGray)
                ) {
                    val gameViewModel = GameViewModelLocal.current
                    val grainCount = gameViewModel.hamsterGame.paule.grainCount

                    Text("CONSOLE\nGRAINS: $grainCount")
                }
            }
        }
    }
}
