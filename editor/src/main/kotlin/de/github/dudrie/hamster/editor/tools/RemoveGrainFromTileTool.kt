package de.github.dudrie.hamster.editor.tools

import de.github.dudrie.hamster.core.model.kachel.KornInhalt
import de.github.dudrie.hamster.core.model.kachel.Leer
import de.github.dudrie.hamster.core.model.util.Position
import de.github.dudrie.hamster.editor.model.EditorUIState

data object RemoveGrainFromTileTool : TileTool() {

    override fun apply(position: Position, state: EditorUIState) {
        val tile = state.getTileAt(position)
        val inhalt = tile.inhalt

        if (inhalt !is KornInhalt) {
            return
        }

        val neueAnzahl = inhalt.anzahl - 1
        val newTile = if (neueAnzahl == 0) {
            tile.copy(inhalt = Leer)
        } else {
            tile.copy(inhalt = inhalt.copy(anzahl = neueAnzahl))
        }

        state.replaceTile(position, newTile)
    }

}
