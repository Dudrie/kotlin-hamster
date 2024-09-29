package de.github.dudrie.hamster.ui.components.console

import androidx.compose.animation.*
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.core.model.util.HamsterString
import de.github.dudrie.hamster.ui.extensions.getText
import de.github.dudrie.hamster.ui.generated.Res
import de.github.dudrie.hamster.ui.generated.console_kommando_nummer
import org.jetbrains.compose.resources.stringResource

@Composable
fun ConsoleMessage(message: HamsterString, visible: Boolean, messageNumber: Int) {
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
