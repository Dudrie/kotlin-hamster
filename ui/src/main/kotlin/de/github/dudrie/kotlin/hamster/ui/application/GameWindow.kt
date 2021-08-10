package de.github.dudrie.kotlin.hamster.ui.application

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.launchApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import java.util.concurrent.CountDownLatch

class GameWindow {
    fun show(initLatch: CountDownLatch) {
        val scope = CoroutineScope(Dispatchers.Main)

        scope.launchApplication {
            var isStarted by remember { mutableStateOf(false) }

            Window(title = "Single Hamster Game", onCloseRequest = ::exitApplication) {
                MaterialTheme {
                    if (isStarted) {
                        MainGameUI()
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
