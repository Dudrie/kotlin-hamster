package de.github.dudrie.hamster.ui.editor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * Toolbox composable which contains different tools to use in the editor.
 */
@Composable
fun EditorToolbox() {
    Column(Modifier.background(Color.Yellow).fillMaxSize()) {
        Text("TOOLBOX")
    }
}

