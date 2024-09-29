package de.github.dudrie.hamster.ui.components.console

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.github.dudrie.hamster.ui.model.UIViewModel

@Composable
fun ConsolePanel(modifier: Modifier = Modifier, viewModel: UIViewModel = viewModel()) {
    val messages by viewModel.spielLog.nachrichten.collectAsState()
    val showUntilNumber by viewModel.spielLog.zeigeBisIndex.collectAsState()
    val scrollState = rememberScrollState()

    Box(modifier = modifier) {
        Column(
            modifier = Modifier.verticalScroll(scrollState).padding(end = 10.dp, bottom = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Spacer(Modifier.height(0.dp))

            messages.forEachIndexed { index, message ->
                key(message) {
                    val messageNumber = messages.size - index
                    ConsoleMessage(
                        message = message,
                        messageNumber = messageNumber,
                        visible = messageNumber <= showUntilNumber
                    )
                }
            }
        }

        VerticalScrollbar(
            adapter = rememberScrollbarAdapter(scrollState),
            modifier = Modifier.fillMaxHeight().align(Alignment.CenterEnd)
        )
    }
}
