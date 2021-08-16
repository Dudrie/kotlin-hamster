package de.github.dudrie.hamster.ui.components.console

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import de.github.dudrie.hamster.ui.application.HamsterGameLocal

/**
 *
 * @param scrollState Scroll state used to control the position of the [LazyColumn].
 * @param modifier Modifiers to change the visual appearance of the underlying [LazyColumn].
 */
@Composable
fun ConsoleMessageList(scrollState: LazyListState, modifier: Modifier = Modifier) {
    val commands = HamsterGameLocal.current.gameCommands
    val messages = commands.gameMessages

    LazyColumn(modifier = modifier, state = scrollState) {
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
