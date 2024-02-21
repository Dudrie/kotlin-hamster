package de.github.dudrie.hamster.ui.components.appbar

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.i18n.HamsterString
import de.github.dudrie.hamster.internal.model.game.GameMode
import de.github.dudrie.hamster.ui.R
import de.github.dudrie.hamster.ui.application.LocalHamsterGame
import de.github.dudrie.hamster.ui.application.LocalUIState
import de.github.dudrie.hamster.ui.components.ControlButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.math.floor

/**
 * AppBar which is shown at the top of the window.
 *
 * It contains the basic controls for the game.
 */
@Composable
fun AppBar() {
    TopAppBar(elevation = 8.dp, contentPadding = PaddingValues(horizontal = 16.dp)) {
        val uiState = LocalUIState.current
        val isHamsterHidden = uiState.hideHamster
        val scope = rememberCoroutineScope { Dispatchers.Default }
        val padding = 8.dp

        val commands = LocalHamsterGame.current.gameCommands
        val canUndo by commands.canUndoCommand
        val canRedo by commands.canRedoCommand
        val canPauseOrResume by commands.canPauseOrResumeGame

        val speedText by produceState("", commands.speed) {
            value = "${HamsterString.get("appbar.speed.label")}: ${floor(commands.speed)}"
        }

        Spacer(Modifier.width(64.dp))

        ControlButton(
            resourcePath = R.icons.undo,
            onClick = { scope.launch { commands.undo() } },
            modifier = Modifier.padding(start = padding),
            enabled = canUndo
        )

        ControlButton(
            resourcePath = if (commands.mode == GameMode.Running) R.icons.pause else R.icons.play,
            modifier = Modifier.padding(start = padding),
            enabled = canPauseOrResume && !isHamsterHidden,
            onClick = {
                scope.launch {
                    if (commands.mode == GameMode.Running) {
                        commands.pauseGame()
                    } else if (commands.mode == GameMode.Paused) {
                        commands.resumeGame()
                    }
                }
            },

            )

        ControlButton(
            resourcePath = R.icons.redo,
            onClick = { scope.launch { commands.redo() } },
            modifier = Modifier.padding(start = padding),
            enabled = canRedo
        )

        ControlButton(
            resourcePath = if (isHamsterHidden) R.icons.hamsterInvisible else R.icons.hamsterVisible,
            onClick = { scope.launch { uiState.changeHamsterHiddenState() } },
            modifier = Modifier.padding(start = padding * 2),
            enabled = commands.mode != GameMode.Running
        )

        SpeedSlider(modifier = Modifier.width(400.dp).padding(start = padding * 2))

        Text(
            text = "${HamsterString.getForGameMode(commands.mode)} | $speedText",
            textAlign = TextAlign.End,
            modifier = Modifier.fillMaxWidth().weight(0.5f)
        )
    }
}
