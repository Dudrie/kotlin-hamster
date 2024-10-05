package de.github.dudrie.hamster.editor.tools

import de.github.dudrie.hamster.core.model.kachel.Leer
import de.github.dudrie.hamster.core.model.util.Position
import de.github.dudrie.hamster.editor.model.EditorUIState

data object MakeFloorTool : TileTool() {

    override fun apply(position: Position, state: EditorUIState) {
        state.replaceTile(position, state.getTileAt(position).copy(inhalt = Leer))
    }

}
