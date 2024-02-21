package de.github.dudrie.hamster.ui.components.appbar

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.internal.model.game.GameCommandStack
import de.github.dudrie.hamster.ui.R
import de.github.dudrie.hamster.ui.application.LocalHamsterGame
import de.github.dudrie.hamster.ui.components.ResourceIcon

/**
 * [Slider] which is responsible for adjusting the [speed][GameCommandStack.setGameSpeed] of the game.
 */
@Composable
fun SpeedSlider(modifier: Modifier = Modifier) {
    val commands = LocalHamsterGame.current.gameCommands

    Row(modifier, verticalAlignment = Alignment.CenterVertically) {
        ResourceIcon(
            resourcePath = R.icons.slowSpeed,
            tint = MaterialTheme.colors.onPrimary,
        )

        Slider(
            value = commands.speed,
            onValueChange = { newSpeed -> commands.setGameSpeed(newSpeed) },
            valueRange = GameCommandStack.speedRange,
            steps = GameCommandStack.speedSteps,
            modifier = Modifier.weight(1f).padding(horizontal = 4.dp),
            colors = SliderDefaults.colors(
                thumbColor = MaterialTheme.colors.onPrimary,
                activeTrackColor = MaterialTheme.colors.secondary,
                inactiveTrackColor = MaterialTheme.colors.onPrimary,
            )
        )

        ResourceIcon(
            resourcePath = R.icons.fastSpeed,
            tint = MaterialTheme.colors.onPrimary,
        )
    }
}
