package de.github.dudrie.hamster.editor.tools

import de.github.dudrie.hamster.core.model.util.Position
import de.github.dudrie.hamster.editor.model.EditorUIState

data object SelectTileTool : TileTool() {

    override fun apply(position: Position, state: EditorUIState) {
        if (state.selectedPosition == position) {
            state.selectedPosition = null
        } else {
            state.selectedPosition = position
        }
    }

}
