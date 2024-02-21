package de.github.dudrie.hamster.ui.components.console

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.mouse.MouseScrollEvent
import androidx.compose.ui.input.mouse.mouseScrollFilter
import androidx.compose.ui.unit.IntSize
import de.github.dudrie.hamster.ui.model.GameMessage
import de.github.dudrie.hamster.ui.model.GameMessageType

/**
 *
 * @param scrollState Scroll state used to control the position of the [LazyColumn].
 * @param modifier Modifiers to change the visual appearance of the underlying [LazyColumn].
 */
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ConsoleMessageList(
    messages: List<GameMessage>,
    scrollState: LazyListState,
    onMouseScroll: (event: MouseScrollEvent, bounds: IntSize) -> Boolean = { _, _ -> false },
    modifier: Modifier = Modifier
) {
    val rowModifier = Modifier.mouseScrollFilter(onMouseScroll)

    LazyColumn(modifier = modifier, state = scrollState) {
        itemsIndexed(messages) { index, message ->
            when (message.type) {
                GameMessageType.COMMAND -> {
                    if (index % 2 == 0) {
                        ConsoleDarkRow(text = message.text, modifier = rowModifier)
                    } else {
                        ConsoleLightRow(text = message.text, modifier = rowModifier)
                    }
                }

                GameMessageType.ERROR -> ConsoleErrorRow(text = message.text, modifier = rowModifier)
                GameMessageType.INFO -> ConsoleInfoRow(text = message.text, modifier = rowModifier)
            }
        }
    }
}
