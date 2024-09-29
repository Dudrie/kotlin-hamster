package de.github.dudrie.hamster.ui.components.console

import androidx.compose.animation.*
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.github.dudrie.hamster.ui.extensions.getText
import de.github.dudrie.hamster.ui.generated.Res
import de.github.dudrie.hamster.ui.generated.console_kommando_nummer
import de.github.dudrie.hamster.ui.model.UIViewModel
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.stringResource

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
                    var visible by remember { mutableStateOf(false) }

                    LaunchedEffect(showUntilNumber) {
                        visible = messageNumber <= showUntilNumber + 1
                    }

                    AnimatedVisibility(
                        visible = visible,
                        enter = slideIn { IntOffset(it.width, 0) } + fadeIn(),
                        exit = slideOut { IntOffset(it.width, 0) } + fadeOut(),
                        label = "Message$messageNumber",
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Card(Modifier.fillMaxWidth()) {
                            Text(
                                text = stringResource(
                                    Res.string.console_kommando_nummer,
                                    messageNumber
                                ),
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(
                                    start = 8.dp,
                                    end = 8.dp,
                                    top = 6.dp,
                                    bottom = 2.dp
                                )
                            )

                            HorizontalDivider()

                            Text(
                                text = message.getText(),
                                modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp)
                            )
                        }
                    }
                }
            }
        }

        VerticalScrollbar(
            adapter = rememberScrollbarAdapter(scrollState),
            modifier = Modifier.fillMaxHeight().align(Alignment.CenterEnd)
        )
    }
}
