package de.github.dudrie.hamster.editor.application

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.FrameWindowScope
import de.github.dudrie.hamster.editor.dialog.DialogManager
import de.github.dudrie.hamster.editor.i18n.EditorString
import de.github.dudrie.hamster.ui.application.windows.ApplicationWindow
import de.github.dudrie.hamster.ui.theme.ThemeWrapper

/**
 * Window showing the editor's UI.
 */
class EditorWindow : ApplicationWindow(EditorString.get("window.editor.title")) {

    /**
     * Renders the UI for the editor.
     */
    @Composable
    override fun FrameWindowScope.content() {
        ThemeWrapper {
            MainEditorUI()

            DialogManager()
        }
    }
}
