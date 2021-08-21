package de.github.dudrie.hamster.ui.components.board

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import de.github.dudrie.hamster.i18n.HamsterString
import de.github.dudrie.hamster.ui.R
import de.github.dudrie.hamster.ui.components.ResourceIcon
import de.github.dudrie.hamster.ui.components.ResourceIconSize

/**
 * Special [Popup] which shows the [RuntimeException] of the game if there is any.
 */
@Composable
fun BoardExceptionDialog(runtimeException: RuntimeException?) {
    var showErrorDialog by remember(runtimeException) { mutableStateOf(runtimeException != null) }

    runtimeException?.let {
        if (!showErrorDialog) {
            return
        }

        Popup(alignment = Alignment.Center, focusable = true) {
            Surface(
                elevation = 24.dp,
                modifier = Modifier.widthIn(min = 300.dp, max = 500.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Column {
                    Box(
                        Modifier.background(MaterialTheme.colors.error).padding(horizontal = 16.dp, vertical = 8.dp)
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = HamsterString.get("dialog.exception.title"),
                            style = MaterialTheme.typography.h5.copy(color = MaterialTheme.colors.onError)
                        )
                    }

                    Row(
                        Modifier.padding(start = 16.dp, end = 16.dp, top = 24.dp, bottom = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        ResourceIcon(
                            R.icons.error,
                            tint = MaterialTheme.colors.error,
                            size = ResourceIconSize.Large
                        )

                        Text(
                            text = "${HamsterString.get("dialog.exception.pretext")}:\n\"$runtimeException\"",
                            style = MaterialTheme.typography.body2,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }

                    Box(Modifier.align(Alignment.End).padding(start = 16.dp, end = 16.dp, bottom = 8.dp)) {
                        TextButton(
                            onClick = { showErrorDialog = false },
                            colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colors.onSurface)
                        ) {
                            Text(
                                HamsterString.get("button.okay").uppercase()
                            )
                        }
                    }
                }
            }
        }
    }
}
