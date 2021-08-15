package de.github.dudrie.hamster.ui.components.console

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.defaultScrollbarStyle
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
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
        val messageCount by commands.gameMessageCount
        val scrollState = rememberLazyListState()

        LaunchedEffect(messageCount) {
            if (messageCount > 0) {
                // FIXME: If one moves the scrollbar during the auto-scroll the UI freezes.
                //       This is NOT the case with scrollToItem. How to solve this problem?
                scrollState.animateScrollToItem(messageCount - 1)
            }
        }

        Box {
            LazyColumn(modifier = Modifier.fillMaxSize().padding(end = 8.dp), state = scrollState) {
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

            // TODO: Make prettier.
            VerticalScrollbar(
                adapter = rememberScrollbarAdapter(scrollState),
                modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
                style = defaultScrollbarStyle().copy(
                    unhoverColor = Color.Black.copy(alpha = 0.4f),
                    hoverColor = MaterialTheme.colors.primary
                )
            )
        }

    }
}
