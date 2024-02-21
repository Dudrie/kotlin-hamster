package de.github.dudrie.hamster.ui.application.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class UIState {
    var hideHamster by mutableStateOf(false)
        private set

    fun setHamsterHiddenState(hide: Boolean) {
        hideHamster = hide
    }
}
