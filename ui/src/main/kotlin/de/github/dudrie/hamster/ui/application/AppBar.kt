package de.github.dudrie.hamster.ui.application

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.internal.model.game.GameMode
import de.github.dudrie.hamster.ui.R
import de.github.dudrie.hamster.ui.components.ControlButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun AppBar() {
    TopAppBar(elevation = 8.dp, contentPadding = PaddingValues(horizontal = 16.dp)) {
        val commands = HamsterGameLocal.current.gameCommands
        val padding = 8.dp
        val canUndo by produceState(false, commands.mode, commands.hasCommandsToUndo.value) {
            value = commands.hasCommandsToUndo.value && commands.mode == GameMode.Paused
        }
        val canRedo by produceState(false, commands.mode, commands.hasCommandsToRedo.value) {
            value = commands.hasCommandsToRedo.value && commands.mode == GameMode.Paused
        }
        val canPauseOrResume by produceState(false, commands.mode) {
            value = commands.mode == GameMode.Running || commands.mode == GameMode.Paused
        }

        Spacer(Modifier.width(64.dp))

        ControlButton(
            resourcePath = R.icons.undo,
            onClick = { commands.undo() },
            modifier = Modifier.padding(start = padding),
            enabled = canUndo
        )

        ControlButton(
            resourcePath = if (commands.mode == GameMode.Running) R.icons.pause else R.icons.play,
            modifier = Modifier.padding(start = padding),
            enabled = canPauseOrResume,
            onClick = {
                // Make sure we run this in a different coroutine so the UI does not wait until the methods return.
                CoroutineScope(Dispatchers.Default).launch {
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
            onClick = { commands.redo() },
            modifier = Modifier.padding(start = padding),
            enabled = canRedo
        )

        Text(text = "${commands.mode}", textAlign = TextAlign.End, modifier = Modifier.fillMaxWidth())
    }
}
