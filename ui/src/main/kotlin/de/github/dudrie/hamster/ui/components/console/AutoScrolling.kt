package de.github.dudrie.hamster.ui.components.console

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*

/**
 * Composable helper that handles the logic for implementing auto scrolling behaviour in a list.
 *
 * The necessary [AutoScrollHelpers] are returned.
 */
@Composable
fun handleAutoScrolling(messageCount: Int): AutoScrollHelpers {
    val actualMessageCount by rememberUpdatedState(messageCount)
    val doAutoScroll = remember { mutableStateOf(true) }
    val interactionSource = remember { MutableInteractionSource() }
    val scrollState = rememberLazyListState()

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collect { interaction ->
            if (interaction is DragInteraction.Start) {
                doAutoScroll.value = false
            }
        }
    }

    LaunchedEffect(actualMessageCount) {
        if (doAutoScroll.value) {
            scrollState.scrollToLastMessage(actualMessageCount)
        }
    }

    return remember(doAutoScroll, scrollState, interactionSource) {
        AutoScrollHelpers(
            doAutoScroll,
            scrollState,
            interactionSource
        )
    }
}

/**
 * Necessary helper objects that can be passed to a [LazyColumn] and a [VerticalScrollbar].
 *
 * @param doAutoScroll State that indicates if auto scrolling should be used. This is a [MutableState].
 * @param scrollState State that should be passed as `scrollState` to a [LazyColumn] and it should be used for the [ScrollbarAdapter][androidx.compose.foundation.ScrollbarAdapter] of a [VerticalScrollbar].
 * @param interactionSource Source for interactions with the [VerticalScrollbar].
 */
data class AutoScrollHelpers(
    var doAutoScroll: MutableState<Boolean>,
    val scrollState: LazyListState,
    val interactionSource: MutableInteractionSource
)
