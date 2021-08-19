package de.github.dudrie.hamster.editor

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.editor.dialog.DialogManager

/**
 * Main composable for the editor UI.
 */
@Composable
fun MainEditorUI() {
    Scaffold(topBar = { EditorAppBar() }) {
        Row(modifier = Modifier.fillMaxSize()) {
            val territory by EditorState.territory

            key(territory) {
                BoardForEditor(territory, Modifier.weight(1f).fillMaxHeight())
            }

            EditorSidepanel(Modifier.fillMaxHeight().width(300.dp))
        }
    }

    DialogManager()
}
