package de.github.dudrie.hamster.editor.application

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import de.github.dudrie.hamster.editor.components.BoardForEditor
import de.github.dudrie.hamster.editor.sidepanel.EditorSidepanel

/**
 * Main composable for the editor UI.
 */
@Composable
fun MainEditorUI() {
    val scaffoldState = rememberScaffoldState()

    CompositionLocalProvider(SnackbarHostLocal provides scaffoldState.snackbarHostState) {
        Scaffold(topBar = { EditorAppBar() }, scaffoldState = scaffoldState) {
            Row(modifier = Modifier.fillMaxSize()) {
                val territory by EditorState.territory

                key(territory, territory.territorySize) {
                    BoardForEditor(territory, Modifier.weight(1f).fillMaxHeight())
                }

                EditorSidepanel(Modifier.fillMaxHeight().width(300.dp))
            }
        }
    }
}

internal val SnackbarHostLocal = compositionLocalOf<SnackbarHostState> { error("No SnackbarHostState was provided.") }
