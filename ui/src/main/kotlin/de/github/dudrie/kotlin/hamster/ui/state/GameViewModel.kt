package de.github.dudrie.kotlin.hamster.ui.state

import androidx.compose.runtime.mutableStateOf

val gameViewModel = GameViewModel()

class GameViewModel {
    var grainCount = mutableStateOf(12)
}
