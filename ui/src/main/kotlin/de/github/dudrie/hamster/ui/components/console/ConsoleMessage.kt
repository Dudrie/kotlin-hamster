package de.github.dudrie.hamster.ui.components.console

import androidx.compose.animation.*
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.core.model.util.HamsterString
import de.github.dudrie.hamster.ui.extensions.getText
import de.github.dudrie.hamster.ui.generated.*
import org.jetbrains.compose.resources.stringResource

@Composable
fun ConsoleCommandMessage(
    message: HamsterString,
    visible: Boolean,
    messageNumber: Int
) {
    ConsoleMessage(
        message = message,
        visible = visible,
        title = stringResource(
            Res.string.console_kommando_nummer,
            messageNumber
        )
    )
}

@Composable
fun ConsoleCommandCountMessage(count: Int) {
    var visible by remember(count) { mutableStateOf(false) }

    LaunchedEffect(count) {
        visible = true
    }

    ConsoleMessage(
        message = stringResource(Res.string.console_kommando_anzahl_inhalt, count),
        visible = visible,
        title = stringResource(Res.string.console_kommando_anzahl_titel),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
    )
}

@Composable
fun ConsoleErrorMessage(
    message: HamsterString,
) {
    var visible by remember(message) { mutableStateOf(false) }

    LaunchedEffect(message) {
        visible = true
    }

    ConsoleMessage(
        message = message,
        visible = visible,
        title = stringResource(Res.string.console_fehler_titel),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.error)
    )
}

@Composable
private fun ConsoleMessage(
    message: HamsterString,
    visible: Boolean,
    title: String,
    colors: CardColors = CardDefaults.cardColors()
) {
    ConsoleMessage(message = message.getText(), visible = visible, title = title, colors = colors)
}

@Composable
private fun ConsoleMessage(
    message: String,
    visible: Boolean,
    title: String,
    colors: CardColors = CardDefaults.cardColors()
) {
    AnimatedVisibility(
        visible = visible,
        enter = slideIn { IntOffset(it.width, 0) } + fadeIn(),
        exit = slideOut { IntOffset(it.width, 0) } + fadeOut(),
        modifier = Modifier.fillMaxWidth()
    ) {
        Card(modifier = Modifier.fillMaxWidth(), colors = colors) {
            Text(
                text = title,
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
                text = message,
                modifier = Modifier.padding(vertical = 8.dp, horizontal = 8.dp)
            )
        }
    }
}
