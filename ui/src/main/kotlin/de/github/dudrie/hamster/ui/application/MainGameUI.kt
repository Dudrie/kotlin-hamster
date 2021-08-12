package de.github.dudrie.hamster.ui.application

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.internal.model.game.GameMode

@Composable
fun MainGameUI() {
    Scaffold(
        topBar = {
            TopAppBar(elevation = 8.dp, contentPadding = PaddingValues(horizontal = 16.dp)) {
                val commands = HamsterGameLocal.current.gameCommands
                Text(
                    text = "CONTROLS",
                    color = MaterialTheme.colors.onPrimary,
                    style = MaterialTheme.typography.h4
                )
                Spacer(Modifier.width(8.dp))
                Button(
                    onClick = {
                        if (commands.mode == GameMode.Running) {
                            commands.pauseGame()
                        } else if (commands.mode == GameMode.Paused) {
                            commands.resumeGame()
                        }
                    },
                    colors = ButtonDefaults.outlinedButtonColors()
                ) {
                    Text(text = if (commands.mode == GameMode.Paused) "Resume" else "Pause")
                }

                Text(text = "${commands.mode}", textAlign = TextAlign.End, modifier = Modifier.fillMaxWidth())
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
                val viewModel = HamsterGameLocal.current.hamsterGameViewModel
                val commands = HamsterGameLocal.current.gameCommands
                val grainCount = viewModel.hamster.grainCount
                val speed = commands.speed

                Text("CONSOLE\nGRAINS: $grainCount\nSPEED:$speed")
            }
        }
    }
}
