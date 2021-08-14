package de.github.dudrie.hamster.ui.application

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.internal.model.game.GameCommandStack
import de.github.dudrie.hamster.internal.model.game.GameMode
import de.github.dudrie.hamster.ui.R
import de.github.dudrie.hamster.ui.components.ControlButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun AppBar() {
    TopAppBar(elevation = 8.dp, contentPadding = PaddingValues(horizontal = 16.dp)) {
        val scope = rememberCoroutineScope { Dispatchers.Default }
        val padding = 8.dp

        val commands = HamsterGameLocal.current.gameCommands
        val canUndo by commands.canUndoCommand()
        val canRedo by commands.canRedoCommand()
        val canPauseOrResume by commands.canPauseOrResumeGame()

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
            enabled = canPauseOrResume,
            onClick = {
                // Make sure we run this in a different coroutine so the UI does not wait until the methods return.
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

        // TODO: Add icons (& current value? (floored!))
        Slider(
            value = commands.speed,
            onValueChange = { newSpeed -> commands.setSpeed(newSpeed) },
            valueRange = GameCommandStack.speedRange,
            steps = GameCommandStack.speedSteps,
            modifier = Modifier.weight(1f).padding(start = padding),
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colors.onPrimary,
                activeTrackColor = MaterialTheme.colors.secondary,
                inactiveTrackColor = MaterialTheme.colors.onPrimary,
            )
        )

        Text(
            text = "${commands.mode} | ${commands.speed}",
            textAlign = TextAlign.End,
            modifier = Modifier.fillMaxWidth().weight(0.5f)
        )
    }
}
