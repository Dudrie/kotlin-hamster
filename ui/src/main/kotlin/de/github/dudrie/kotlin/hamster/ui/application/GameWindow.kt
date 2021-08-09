package de.github.dudrie.kotlin.hamster.ui.application

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.launchApplication
import de.github.dudrie.kotlin.hamster.ui.state.GameViewModel
import de.github.dudrie.kotlin.hamster.ui.state.gameViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import java.util.concurrent.CountDownLatch

private val GameViewModelLocal = compositionLocalOf<GameViewModel> { error("No GameViewModel is provided.") }

@Composable
fun GrainDisplay() {
    val gameViewModel = GameViewModelLocal.current
    val grainCount by gameViewModel.grainCount

    Text("Grain Count Display: $grainCount")
}

@Composable
fun GrainIncreaseButton() {
    var grainCount by GameViewModelLocal.current.grainCount

    Button(onClick = { grainCount++ }) {
        Text("Increase grains!!!")
    }
}

@Composable
fun GrainDecreaseButton() {
    var grainCount by GameViewModelLocal.current.grainCount

    Button(onClick = { grainCount-- }) {
        Text("Decrease grains!!!")
    }
}

@Composable
fun RootContent() {
    CompositionLocalProvider(GameViewModelLocal provides gameViewModel) {
        MaterialTheme {
            Column(Modifier.padding(4.dp).background(Color.Red).fillMaxSize()) {
                GrainDisplay()

                Row(Modifier.padding(top = 16.dp)) {
                    GrainDecreaseButton()
                    Spacer(Modifier.width(8.dp))
                    GrainIncreaseButton()
                }
            }
        }
    }
}

class GameWindow {
    fun show(initLatch: CountDownLatch) {
        val scope = CoroutineScope(Dispatchers.Main)

        scope.launchApplication {
            var isStarted by remember { mutableStateOf(false) }

            Window(title = "Single Hamster Game", onCloseRequest = ::exitApplication) {
                if (isStarted) {
                    RootContent()
                } else {
                    // TODO: Does one need a loading indicator here?
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Starting hamster app...")
                    }
                }
            }

            LaunchedEffect(true) {
                // TODO: Init game here?!
                delay(3000L)
                isStarted = true
                delay(500L)
                initLatch.countDown()
            }
        }
    }
}
