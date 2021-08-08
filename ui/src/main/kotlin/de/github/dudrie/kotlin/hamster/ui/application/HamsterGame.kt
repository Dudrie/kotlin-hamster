package de.github.dudrie.kotlin.hamster.ui.application

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

class HamsterGame {
    fun startGame() {
        application {
            Window(title = "Hamster App", onCloseRequest = ::exitApplication) {
                MaterialTheme {
                    Text("Hamster Game started!!!")
                }
            }
        }
    }
}