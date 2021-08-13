package de.github.dudrie.hamster.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.ui.application.HamsterGameLocal

@Composable
fun ConsolePanel(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.background(Color.LightGray)
    ) {
        val commands = HamsterGameLocal.current.gameCommands
        val messages = commands.gameMessages

        Column {
            // TODO: Remove this one and replace with dialog!
            if (commands.runtimeException != null) {
                Text("Exception: ${commands.runtimeException}", color = Color.Magenta)
                Spacer(Modifier.height(16.dp))
            }

            LazyColumn(Modifier.fillMaxSize()) {
                itemsIndexed(messages.toList()) { index, message ->
                    val backgroundColor: Color = if (index % 2 == 0) Color.Gray else Color.White
                    val textColor: Color = if (index % 2 == 0) Color.White else Color.Black

                    Box(Modifier.fillMaxSize().background(backgroundColor)) {
                        Text(text = message, modifier = Modifier.padding(4.dp), color = textColor)
                    }
                }
            }
        }
    }
}
