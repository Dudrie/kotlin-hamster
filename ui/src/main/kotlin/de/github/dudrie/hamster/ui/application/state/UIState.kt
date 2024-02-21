package de.github.dudrie.hamster.ui.application.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class UIState {

    var hideHamster by mutableStateOf(false)
        private set

    var showConsole by mutableStateOf(true)

    fun changeHamsterHiddenState() {
        hideHamster != hideHamster
    }
}
