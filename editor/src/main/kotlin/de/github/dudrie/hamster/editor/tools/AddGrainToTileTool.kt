package de.github.dudrie.hamster.editor.tools

import de.github.dudrie.hamster.core.model.kachel.KornInhalt
import de.github.dudrie.hamster.core.model.kachel.Leer
import de.github.dudrie.hamster.core.model.util.Position
import de.github.dudrie.hamster.editor.model.EditorUIState

data object AddGrainToTileTool : TileTool() {

    override fun apply(position: Position, state: EditorUIState) {
        val tile = state.getTileAt(position)
        val inhalt = tile.inhalt
        if (!(inhalt is Leer || inhalt is KornInhalt)) {
            return
        }

        val newTile = if (inhalt is KornInhalt) {
            tile.copy(inhalt = inhalt.copy(anzahl = inhalt.anzahl + 1))
        } else {
            tile.copy(inhalt = KornInhalt(1))
        }

        state.replaceTile(position, newTile)
    }

}
