package de.github.dudrie.hamster.ui.components.console

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Base abstraction of a row used inside the [ConsolePanel].
 *
 * @see ConsoleLightRow
 * @see ConsoleDarkRow
 * @see ConsoleErrorRow
 */
@Composable
private fun ConsoleRow(
    text: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Unspecified,
    textColor: Color = Color.Unspecified
) {
    Box(modifier.fillMaxSize().background(backgroundColor)) {
        Text(text = text, modifier = Modifier.padding(4.dp), color = textColor)
    }
}

/**
 * [ConsoleRow] showing the given [text] on a light background.
 *
 * @see ConsoleRow
 */
@Composable
fun ConsoleLightRow(text: String, modifier: Modifier = Modifier) {
    ConsoleRow(
        text = text,
        modifier = modifier,
        backgroundColor = Color.White,
        textColor = Color.Black
    )
}

/**
 * [ConsoleRow] showing the given [text] on a dark background.
 *
 * @see ConsoleRow
 */
@Composable
fun ConsoleDarkRow(text: String, modifier: Modifier = Modifier) {
    ConsoleRow(
        text = text,
        modifier = modifier,
        backgroundColor = Color.Gray,
        textColor = Color.White
    )
}

/**
 * [ConsoleRow] that displays the [exception] on a row which has the [error][MaterialTheme.colors] background.
 *
 * @see ConsoleRow
 */
@Composable
fun ConsoleErrorRow(exception: RuntimeException, modifier: Modifier = Modifier) {
    ConsoleRow(
        text = "$exception",
        modifier = modifier,
        backgroundColor = MaterialTheme.colors.error,
        textColor = MaterialTheme.colors.onError
    )
}
