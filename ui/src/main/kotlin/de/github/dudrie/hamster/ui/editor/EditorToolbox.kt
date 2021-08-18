package de.github.dudrie.hamster.ui.editor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/**
 * Toolbox composable which contains different tools to use in the editor.
 */
@Composable
fun EditorToolbox() {
    Column(Modifier.background(Color.Black.copy(alpha = 0.1f)).fillMaxSize().padding(8.dp)) {
        Text("TOOLBOX")
    }
}

