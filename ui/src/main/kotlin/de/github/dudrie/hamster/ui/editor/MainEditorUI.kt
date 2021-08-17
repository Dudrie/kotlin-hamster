package de.github.dudrie.hamster.ui.editor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.interfaces.AbstractEditableTerritory

/**
 * Main composable for the editor UI.
 *
 * Takes in the [territory] that should be edited in the territory.
 */
@Composable
fun MainEditorUI(territory: AbstractEditableTerritory) {
    Scaffold(
        topBar = {
            TopAppBar(elevation = 8.dp, contentPadding = PaddingValues(horizontal = 16.dp)) {
                Text("MENU & TOOLS & SETTINGS", color = MaterialTheme.colors.onPrimary)
            }
        }
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            BoardForEditor(territory, Modifier.weight(1f).fillMaxHeight())

            EditorToolbox(Modifier.fillMaxHeight().width(300.dp).background(Color.Magenta))
        }
    }
}
