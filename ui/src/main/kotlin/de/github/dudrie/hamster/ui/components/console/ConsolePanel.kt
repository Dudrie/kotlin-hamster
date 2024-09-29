package de.github.dudrie.hamster.ui.components.console

import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import de.github.dudrie.hamster.ui.extensions.getText
import de.github.dudrie.hamster.ui.generated.Res
import de.github.dudrie.hamster.ui.generated.console_kommando_nummer
import de.github.dudrie.hamster.ui.model.UIViewModel
import org.jetbrains.compose.resources.stringResource

@Composable
fun ConsolePanel(modifier: Modifier = Modifier, viewModel: UIViewModel = viewModel()) {
    val nachrichten by viewModel.spielLog.nachrichten.collectAsState()
    val scrollState = rememberScrollState()

    Box(modifier = modifier) {
        Column(
            modifier = Modifier.verticalScroll(scrollState).padding(end = 10.dp, bottom = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Spacer(Modifier.height(0.dp))

            nachrichten.forEachIndexed { index, nachricht ->
                Card(Modifier.fillMaxWidth()) {
                    Text(
                        text = stringResource(
                            Res.string.console_kommando_nummer,
                            nachrichten.size - index
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
                        text = nachricht.getText(),
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp)
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
