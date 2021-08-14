package de.github.dudrie.hamster.ui.components.board

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import de.github.dudrie.hamster.ui.application.HamsterGameLocal

@Composable
fun BoardExceptionDialog() {
    val runtimeException = HamsterGameLocal.current.gameCommands.runtimeException
    var showErrorDialog by remember(runtimeException) { mutableStateOf(runtimeException != null) }

    if (showErrorDialog) {
        Popup(alignment = Alignment.Center, focusable = true, onDismissRequest = { showErrorDialog = false }) {
            Surface(
                elevation = 24.dp,
                modifier = Modifier.defaultMinSize(minWidth = 300.dp, minHeight = 175.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                // TODO: Make me pretty :)
                Box(contentAlignment = Alignment.Center) {
                    Text("$runtimeException")
                }
            }
        }
    }
}
