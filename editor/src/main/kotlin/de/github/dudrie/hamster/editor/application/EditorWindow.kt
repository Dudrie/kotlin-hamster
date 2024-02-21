package de.github.dudrie.hamster.editor.application

import androidx.compose.material.SnackbarHostState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.window.FrameWindowScope
import de.github.dudrie.hamster.editor.dialog.DialogManager
import de.github.dudrie.hamster.editor.i18n.EditorString
import de.github.dudrie.hamster.ui.application.LocalUIState
import de.github.dudrie.hamster.ui.application.state.UIState
import de.github.dudrie.hamster.ui.application.windows.ApplicationWindow

/**
 * Window showing the editor's UI.
 */
class EditorWindow : ApplicationWindow(EditorString.get("window.editor.title")) {

    private val uiState = UIState()

    /**
     * Renders the UI for the editor.
     */
    @Composable
    override fun FrameWindowScope.content() {
        val scaffoldState = rememberScaffoldState()

        CompositionLocalProvider(
            SnackbarHostLocal provides scaffoldState.snackbarHostState,
            LocalUIState provides uiState
        ) {
            MainEditorUI(scaffoldState)

            DialogManager()
        }
    }
}

internal val SnackbarHostLocal = compositionLocalOf<SnackbarHostState> { error("No SnackbarHostState was provided.") }
