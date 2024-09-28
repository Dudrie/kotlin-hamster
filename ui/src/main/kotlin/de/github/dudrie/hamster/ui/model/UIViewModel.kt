package de.github.dudrie.hamster.ui.model

import androidx.lifecycle.ViewModel
import de.github.dudrie.hamster.core.game.SpielViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking
import kotlin.math.roundToInt

class UIViewModel(private val spielViewModel: SpielViewModel) : ViewModel() {
    private val _uiState = MutableStateFlow(UIState())
    val uiState = _uiState.asStateFlow()

    val spielZustand by spielViewModel

    fun setGeschwindigkeit(geschwindigkeit: Float) {
        spielViewModel.setGeschwindigkeit(geschwindigkeit.roundToInt().toDouble())
    }

    fun toggleHamsterVisibility() {
        _uiState.update { it.copy(hideHamster = !it.hideHamster) }
    }

    fun setzeSpielFort() {
        spielViewModel.setzeSpielFort()
    }

    fun pausiereSpiel() {
        runBlocking { spielViewModel.pausiereSpiel() }
    }
}

data class UIState(val showConsole: Boolean = true, val hideHamster: Boolean = false)
