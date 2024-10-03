package de.github.dudrie.hamster.editor.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import de.github.dudrie.hamster.core.extensions.getAbmessungen
import de.github.dudrie.hamster.core.file.SpielExporter
import de.github.dudrie.hamster.core.file.SpielImporter
import de.github.dudrie.hamster.core.model.hamster.InternerHamster
import de.github.dudrie.hamster.core.model.kachel.Kachel
import de.github.dudrie.hamster.core.model.kachel.KornInhalt
import de.github.dudrie.hamster.core.model.kachel.Leer
import de.github.dudrie.hamster.core.model.kachel.Wand
import de.github.dudrie.hamster.core.model.territory.Abmessungen
import de.github.dudrie.hamster.core.model.territory.InternesTerritorium
import de.github.dudrie.hamster.core.model.util.Position
import de.github.dudrie.hamster.core.model.util.Richtung
import de.github.dudrie.hamster.editor.tools.SelectTileTool
import de.github.dudrie.hamster.editor.tools.TileTool
import java.nio.file.Path

class EditorUIState : ViewModel() {

    var hasUnsavedChanges = false
        private set

    var tiles by mutableStateOf(mapOf<Position, Kachel>())
        private set

    private val _hamster = mutableStateMapOf<Position, InternerHamster>()
    val hamster: List<InternerHamster>
        get() = _hamster.values.toList()

    var selectedTool by mutableStateOf<TileTool>(SelectTileTool)

    var selectedPosition by mutableStateOf<Position?>(null)

    init {
        createNewTerritory()
    }

    fun getTileAt(position: Position): Kachel =
        tiles[position] ?: throw NoSuchElementException("ERR_NO_TILE_AT_POSITION")

    fun replaceTile(position: Position, newTile: Kachel) {
        val newTiles = tiles.toMutableMap()
        newTiles[position] = newTile
        tiles = newTiles
        hasUnsavedChanges = true
    }

    fun getHamsterAt(position: Position): InternerHamster? = _hamster[position]

    fun createHamsterAt(position: Position) {
        _hamster[position] = InternerHamster(position, Richtung.Sueden, listOf())
    }

    fun removeHamsterFrom(position: Position) {
        _hamster.remove(position)
    }

    fun turnHamsterAt(position: Position, direction: Richtung) {
        val hamster = _hamster[position]
            ?: throw IllegalArgumentException("Es gibt bei $position keinen Hamster")

        _hamster[position] = hamster.copy(richtung = direction)
    }

    fun createNewTerritory() {
        this.tiles = createEmptyTerritory(Abmessungen(breite = 5, hohe = 3))
        _hamster.clear()
        reset()
    }

    fun saveToFile(filepath: Path) {
        require(_hamster.isNotEmpty()) { "Es muss mindestens einen Hamster geben." }

        val territory = InternesTerritorium(
            kacheln = tiles,
            hamster = _hamster.values.toList(),
            kachelZuMeterSkalierung = 1.0
        )
        SpielExporter.speichereSpiel(filepath.toString(), territory)
        reset()
    }

    fun loadFromFile(filepath: Path) {
        val territory = SpielImporter.ladeTerritoriumAusDatei(filepath)
        tiles = territory.kacheln
        _hamster.clear()
        territory.hamster.forEach {
            _hamster[it.position] = it
        }
        reset()
    }

    private fun reset() {
        hasUnsavedChanges = false
        selectedTool = SelectTileTool
        selectedPosition = null
    }

    fun surroundWithWalls() {
        val size = tiles.getAbmessungen()

        repeat(size.breite) { column ->
            makeWallIfPossible(Position(x = column, y = 0))
            makeWallIfPossible(Position(x = column, y = size.hohe - 1))
        }

        for (row in 1 until size.hohe - 1) {
            makeWallIfPossible(Position(x = 0, y = row))
            makeWallIfPossible(Position(x = size.breite - 1, y = row))
        }
    }

    private fun makeWallIfPossible(position: Position) {
        val tile = getTileAt(position)
        if (tile.inhalt is KornInhalt || hamster.any { it.position == position }) return

        replaceTile(position, tile.copy(inhalt = Wand))
    }

    fun changeTerritorySize(newSize: Abmessungen) {
        val newTiles = createEmptyTerritory(newSize).toMutableMap()

        tiles.entries.forEach { (position, tile) ->
            if (newSize.istInnerhalb(position)) {
                newTiles[position] = tile.copy()
            }
        }

        tiles = newTiles
    }

    private fun createEmptyTerritory(size: Abmessungen): Map<Position, Kachel> {
        val tiles = mutableMapOf<Position, Kachel>()
        repeat(size.hohe) { row ->
            repeat(size.breite) { column ->
                tiles[Position(column, row)] = Kachel(Leer)
            }
        }
        return tiles
    }
}
