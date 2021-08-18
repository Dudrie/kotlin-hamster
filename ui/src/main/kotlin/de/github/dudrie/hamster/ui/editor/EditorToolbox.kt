package de.github.dudrie.hamster.ui.editor

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Toolbox composable which contains different tools to use in the editor.
 */
@Composable
fun EditorToolbox(modifier: Modifier = Modifier) {
    Column(modifier.padding(8.dp)) {
        Text("TOOLBOX")
    }
}

