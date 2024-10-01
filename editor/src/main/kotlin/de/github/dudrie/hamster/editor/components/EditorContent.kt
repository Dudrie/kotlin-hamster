package de.github.dudrie.hamster.editor.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.editor.components.toolbox.ToolBox

@Composable
fun EditorContent(modifier: Modifier = Modifier) {
    Row(modifier) {
        Box(Modifier.weight(1f)) {
            Text("Hallo alle")
        }

        VerticalDivider(Modifier.padding(horizontal = 8.dp))

        ToolBox(Modifier.padding(start = 8.dp, end = 8.dp, top = 12.dp).width(300.dp))
    }
}
