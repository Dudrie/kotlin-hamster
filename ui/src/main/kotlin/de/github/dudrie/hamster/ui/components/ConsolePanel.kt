package de.github.dudrie.hamster.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import de.github.dudrie.hamster.ui.application.HamsterGameLocal

@Composable
fun ConsolePanel(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.background(Color.LightGray)
    ) {
        val viewModel = HamsterGameLocal.current.hamsterGameViewModel
        val commands = HamsterGameLocal.current.gameCommands
        val grainCount = viewModel.hamster.grainCount
        val speed = commands.speed

        Column {
            Text("CONSOLE")
            Text("GRAINS: $grainCount")
            Text("SPEED:$speed")
            if (commands.runtimeException != null) {
                Text("Exception: ${commands.runtimeException}")
            }
        }
    }
}
