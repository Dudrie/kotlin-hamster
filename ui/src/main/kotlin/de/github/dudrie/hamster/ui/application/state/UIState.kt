package de.github.dudrie.hamster.ui.application.state

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class UIState {
    private val hideHamsterState = mutableStateOf(false)

    var hideHamster by hideHamsterState
        private set

    fun setHamsterHiddenState(hide: Boolean) {
        hideHamster = hide
    }
}
