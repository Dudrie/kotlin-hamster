package de.github.dudrie.hamster.editor.components.appbar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun EditorAppBar() {
    Surface(
        modifier = Modifier.height(64.0.dp).fillMaxWidth(),
        shadowElevation = 24.dp,
        color = MaterialTheme.colorScheme.primaryContainer
    ) {}
}
