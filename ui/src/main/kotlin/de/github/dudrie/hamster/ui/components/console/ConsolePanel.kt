package de.github.dudrie.hamster.ui.components.console

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import de.github.dudrie.hamster.ui.application.HamsterGameLocal

/**
 * Shows the game messages in a list.
 *
 * The messages alternate between having a light and a dark background. Messages from exceptions are highlighted with a different background.
 *
 * @see ConsoleLightRow
 * @see ConsoleDarkRow
 * @see ConsoleErrorRow
 */
@Composable
fun ConsolePanel(modifier: Modifier = Modifier) {
    Box(modifier = modifier.background(brush = SolidColor(Color.Black), alpha = 0.05f)) {
        val commands = HamsterGameLocal.current.gameCommands
        val messages = commands.gameMessages

        Column {
            LazyColumn(Modifier.fillMaxSize()) {
                itemsIndexed(messages.toList()) { index, message ->
                    if (index % 2 == 0) {
                        ConsoleDarkRow(text = message)
                    } else {
                        ConsoleLightRow(text = message)
                    }
                }

                if (commands.runtimeException != null) {
                    item {
                        ConsoleErrorRow(commands.runtimeException!!)
                    }
                }
            }
        }
    }
}
