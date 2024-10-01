package de.github.dudrie.hamster.editor.tools

import de.github.dudrie.hamster.core.model.util.Position
import de.github.dudrie.hamster.editor.model.EditorUIState

sealed class TileTool {

    abstract fun apply(position: Position, state: EditorUIState)

}
