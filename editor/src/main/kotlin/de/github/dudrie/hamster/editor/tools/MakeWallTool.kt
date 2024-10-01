package de.github.dudrie.hamster.editor.tools

import de.github.dudrie.hamster.core.model.kachel.Wand
import de.github.dudrie.hamster.core.model.util.Position
import de.github.dudrie.hamster.editor.model.EditorUIState

data object MakeWallTool : TileTool() {

    override fun apply(position: Position, state: EditorUIState) {
        state.replaceTile(position, state.getTileAt(position).copy(inhalt = Wand))
    }

}
