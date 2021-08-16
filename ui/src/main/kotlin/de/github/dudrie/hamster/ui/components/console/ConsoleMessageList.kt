package de.github.dudrie.hamster.ui.components.console

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.mouse.MouseScrollEvent
import androidx.compose.ui.input.mouse.mouseScrollFilter
import androidx.compose.ui.unit.IntSize
import de.github.dudrie.hamster.ui.application.HamsterGameLocal

/**
 *
 * @param scrollState Scroll state used to control the position of the [LazyColumn].
 * @param modifier Modifiers to change the visual appearance of the underlying [LazyColumn].
 */
@Composable
fun ConsoleMessageList(
    scrollState: LazyListState,
    onMouseScroll: (event: MouseScrollEvent, bounds: IntSize) -> Boolean = { _, _ -> false },
    modifier: Modifier = Modifier
) {
    val commands = HamsterGameLocal.current.gameCommands
    val messages = commands.gameMessages

    val rowModifier = Modifier.mouseScrollFilter(onMouseScroll)

    LazyColumn(modifier = modifier, state = scrollState) {
        itemsIndexed(messages.toList()) { index, message ->
            if (index % 2 == 0) {
                ConsoleDarkRow(text = message, modifier = rowModifier)
            } else {
                ConsoleLightRow(text = message, modifier = rowModifier)
            }
        }

        if (commands.runtimeException != null) {
            item {
                ConsoleErrorRow(exception = commands.runtimeException!!, modifier = rowModifier)
            }
        }
    }
}
