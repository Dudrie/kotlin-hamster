package de.github.dudrie.hamster.editor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.editor.dialog.DialogManager
import kotlinx.coroutines.delay

/**
 * Main composable for the editor UI.
 */
@Composable
fun MainEditorUI() {
    Scaffold(topBar = { EditorAppBar() }) {
        Row(modifier = Modifier.fillMaxSize()) {
            val territory by EditorState.territory
            var showBoard by remember(territory) { mutableStateOf(false) }

            LaunchedEffect(territory) {
                // Make sure the old board gets "cleaned" end properly recreated.
                // This makes all internal state subscriptions invalid.
                delay(250L)
                showBoard = true
            }

            if (showBoard) {
                BoardForEditor(territory, Modifier.weight(1f).fillMaxHeight())
            } else {
                Spacer(Modifier.weight(1f).background(Color.Red))
            }

            EditorSidepanel(Modifier.fillMaxHeight().width(300.dp))
        }
    }

    DialogManager()
}
