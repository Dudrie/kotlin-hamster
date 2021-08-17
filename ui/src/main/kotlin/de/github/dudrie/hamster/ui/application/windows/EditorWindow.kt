package de.github.dudrie.hamster.ui.application.windows

import androidx.compose.runtime.Composable
import de.github.dudrie.hamster.ResString
import de.github.dudrie.hamster.interfaces.AbstractEditableTerritory
import de.github.dudrie.hamster.ui.editor.MainEditorUI

/**
 * Window showing the editor's UI.
 */
class EditorWindow(private val territory: AbstractEditableTerritory) :
    ApplicationWindow(ResString.get("window.editor.title")) {

    /**
     * Renders the UI for the editor.
     */
    @Composable
    override fun content() {
        MainEditorUI(territory)
    }
}
