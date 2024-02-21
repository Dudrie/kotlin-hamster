package de.github.dudrie.hamster.ui.components.console

import androidx.compose.foundation.LocalScrollbarStyle
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.i18n.HamsterString
import de.github.dudrie.hamster.internal.model.game.GameMode
import de.github.dudrie.hamster.ui.application.LocalHamsterGame
import de.github.dudrie.hamster.ui.model.GameMessage
import de.github.dudrie.hamster.ui.model.GameMessageType

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
        val messages by produceMessagesForList()
        val messageCount = messages.size
        val (doAutoScrollState, scrollState, interactionSource) = handleAutoScrolling(messageCount)
        var doAutoScroll by doAutoScrollState

        ConsoleMessageList(
            messages = messages,
            scrollState = scrollState,
            onMouseScroll = { _, _ ->
                if (scrollState.firstVisibleItemScrollOffset > 0) {
                    doAutoScroll = false
                }
                false
            },
            modifier = Modifier.fillMaxSize().padding(end = 12.dp)
        )

        VerticalScrollbar(
            adapter = rememberScrollbarAdapter(scrollState),
            modifier = Modifier.align(Alignment.CenterEnd).fillMaxHeight(),
            style = LocalScrollbarStyle.current.copy(thickness = 12.dp),
            interactionSource = interactionSource
        )

        ScrollDownButton(
            visible = !doAutoScroll,
            modifier = Modifier.align(Alignment.BottomEnd).padding(end = 16.dp, bottom = 16.dp),
            onClick = {
                scrollState.scrollToLastMessage(messageCount)
                doAutoScroll = true
            }
        )
    }
}

/**
 * Scrolls to the last message in the list by using the given [messageCount].
 *
 * [messageCount] must be the count of all messages currently in the list.
 */
suspend fun LazyListState.scrollToLastMessage(messageCount: Int) {
    if (messageCount > 0) {
        animateScrollToItem(messageCount - 1)
    }
}

/**
 * Creates a list of messages shown in the console.
 *
 * The list is served as a [State].
 */
@Composable
fun produceMessagesForList(): State<List<GameMessage>> {
    val commands = LocalHamsterGame.current.gameCommands
    val canRedo by commands.canRedoCommand

    return produceState(listOf(), commands.gameMessageCount, commands.gameException, commands.mode, canRedo) {
        val messages = mutableListOf<GameMessage>()

        messages.addAll(commands.gameMessages.map { GameMessage(it, GameMessageType.COMMAND) })

        if (!canRedo) {
            commands.gameException?.let {
                messages.add(
                    GameMessage(
                        HamsterString.getWithFormat(
                            "console.game.exception",
                            it.failedCommand.getCommandLogMessage(),
                            it.exception.toString()
                        ),
                        GameMessageType.ERROR
                    )
                )
            }

            if (commands.mode == GameMode.Stopped) {
                messages.add(
                    GameMessage(
                        HamsterString.getWithFormat(
                            "console.executed.commands.count",
                            commands.commandCount
                        ), GameMessageType.INFO
                    )
                )
            }
        }

        value = messages
    }
}
