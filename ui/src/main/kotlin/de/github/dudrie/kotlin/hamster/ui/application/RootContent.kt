package de.github.dudrie.kotlin.hamster.ui.application

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.primarySurface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.github.dudrie.kotlin.hamster.ui.state.GameViewModel
import de.github.dudrie.kotlin.hamster.ui.state.gameViewModel

internal val GameViewModelLocal = compositionLocalOf<GameViewModel> { error("No GameViewModel is provided.") }

@Composable
fun RootContent() {
    CompositionLocalProvider(GameViewModelLocal provides gameViewModel) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                Modifier.fillMaxWidth().height(IntrinsicSize.Max).background(MaterialTheme.colors.primarySurface)
            ) {
                Text(text = "CONTROLS", color = MaterialTheme.colors.onPrimary, style = MaterialTheme.typography.h4)
            }

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
                    Text("CONSOLE")
                }
            }
        }
    }
}
