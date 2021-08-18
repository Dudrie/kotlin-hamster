package de.github.dudrie.hamster.editor.application

import androidx.compose.runtime.Composable
import de.github.dudrie.hamster.editor.MainEditorUI
import de.github.dudrie.hamster.editor.model.AbstractEditableTerritory
import de.github.dudrie.hamster.ui.application.windows.ApplicationWindow

/**
 * Window showing the editor's UI.
 */
class EditorWindow(private val territory: AbstractEditableTerritory) :
    ApplicationWindow(de.github.dudrie.hamster.ResString.get("window.editor.title")) {

    /**
     * Renders the UI for the editor.
     */
    @Composable
    override fun content() {
        MainEditorUI(territory)
    }
}
