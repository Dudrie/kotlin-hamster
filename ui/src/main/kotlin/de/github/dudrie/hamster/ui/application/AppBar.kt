package de.github.dudrie.hamster.ui.application

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.internal.model.game.GameMode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun AppBar() {
    TopAppBar(elevation = 8.dp, contentPadding = PaddingValues(horizontal = 16.dp)) {
        val commands = HamsterGameLocal.current.gameCommands
        Text(
            text = "CONTROLS",
            color = MaterialTheme.colors.onPrimary,
            style = MaterialTheme.typography.h4
        )
        Spacer(Modifier.width(8.dp))
        Button(
            onClick = {
                // Make sure we run this in a different coroutine so the UI does not wait until the methods return.
                CoroutineScope(Dispatchers.Default).launch {
                    if (commands.mode == GameMode.Running) {
                        commands.pauseGame()
                    } else if (commands.mode == GameMode.Paused) {
                        commands.resumeGame()
                    }
                }
            },
            colors = ButtonDefaults.outlinedButtonColors()
        ) {
            Text(text = if (commands.mode == GameMode.Paused) "Resume" else "Pause")
        }

        Spacer(Modifier.width(8.dp))
        Button(onClick = { commands.undo() }, colors = ButtonDefaults.outlinedButtonColors()) {
            Text("Undo")
        }

        Text(text = "${commands.mode}", textAlign = TextAlign.End, modifier = Modifier.fillMaxWidth())
    }
}
