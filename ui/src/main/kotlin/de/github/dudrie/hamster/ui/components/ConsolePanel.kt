package de.github.dudrie.hamster.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.ui.application.HamsterGameLocal

@Composable
fun ConsolePanel(modifier: Modifier = Modifier) {
    Box(modifier = modifier.background(brush = SolidColor(Color.Black), alpha = 0.05f)) {
        val commands = HamsterGameLocal.current.gameCommands
        val messages = commands.gameMessages

        Column {
            LazyColumn(Modifier.fillMaxSize()) {
                itemsIndexed(messages.toList()) { index, message ->
                    val backgroundColor: Color = if (index % 2 == 0) Color.Gray else Color.White
                    val textColor: Color = if (index % 2 == 0) Color.White else Color.Black

                    Box(Modifier.fillMaxSize().background(backgroundColor)) {
                        Text(text = message, modifier = Modifier.padding(4.dp), color = textColor)
                    }
                }

                // TODO: Remove this one and replace with dialog
                //       Or should a dialog be added additionally?
                if (commands.runtimeException != null) {
                    item {
                        Box(Modifier.fillMaxSize().background(MaterialTheme.colors.error)) {
                            Text(
                                text = "Exception: ${commands.runtimeException}",
                                modifier = Modifier.padding(4.dp),
                                color = MaterialTheme.colors.onError
                            )
                        }
                    }
                }
            }
        }
    }
}
