package de.github.dudrie.hamster.ui.application

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.launchApplication
import de.github.dudrie.hamster.external.model.HamsterGame
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import java.util.concurrent.CountDownLatch

internal val HamsterGameLocal = compositionLocalOf<HamsterGame> { error("No hamster game was provided.") }

class GameWindow(private val hamsterGame: HamsterGame) {
    fun show(initLatch: CountDownLatch) {
        val scope = CoroutineScope(Dispatchers.Main)

        scope.launchApplication {
            var isStarted by remember { mutableStateOf(false) }

            Window(title = "Single Hamster Game", onCloseRequest = ::exitApplication) {
                MaterialTheme {
                    if (isStarted) {
                        CompositionLocalProvider(HamsterGameLocal provides hamsterGame) {
                            MainGameUI()
                        }
                    } else {
                        // TODO: Does one need a loading indicator here?
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("Starting hamster app...")
                        }
                    }
                }
            }

            LaunchedEffect(true) {
                // TODO: Init visual (!) board here?!
                delay(2000L)
                isStarted = true
                delay(1000L)
                initLatch.countDown()
            }
        }
    }
}
