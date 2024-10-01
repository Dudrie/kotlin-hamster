package de.github.dudrie.hamster.editor.model

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import de.github.dudrie.hamster.core.file.SpielExporter
import de.github.dudrie.hamster.core.file.SpielImporter
import de.github.dudrie.hamster.core.model.hamster.InternerHamster
import de.github.dudrie.hamster.core.model.kachel.Kachel
import de.github.dudrie.hamster.core.model.kachel.Leer
import de.github.dudrie.hamster.core.model.territory.InternesTerritorium
import de.github.dudrie.hamster.core.model.util.Position
import de.github.dudrie.hamster.editor.tools.SelectTileTool
import de.github.dudrie.hamster.editor.tools.TileTool
import java.nio.file.Path

class EditorUIState : ViewModel() {

    var hasUnsavedChanges = false
        private set

    var tiles by mutableStateOf(mapOf<Position, Kachel>())
        private set

    private val _hamster = mutableStateListOf<InternerHamster>()
    val hamster: List<InternerHamster>
        get() = _hamster.toList()

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

    fun createNewTerritory() {
        val tiles = mutableMapOf<Position, Kachel>()
        repeat(3) { row ->
            repeat(5) { column ->
                tiles[Position(column, row)] = Kachel(Leer)
            }
        }
        this.tiles = tiles
        _hamster.clear()
        reset()
    }

    fun saveToFile(filepath: Path) {
        require(_hamster.isNotEmpty()) { "Es muss mindestens einen Hamster geben." }

        val territory = InternesTerritorium(
            kacheln = tiles,
            hamster = _hamster,
            kachelZuMeterSkalierung = 1.0
        )
        SpielExporter.speichereSpiel(filepath.toString(), territory)
        reset()
    }

    fun loadFromFile(filepath: Path) {
        val territory = SpielImporter.ladeTerritoriumAusDatei(filepath)
        tiles = territory.kacheln
        _hamster.clear()
        _hamster.addAll(territory.hamster)
        reset()
    }

    private fun reset() {
        hasUnsavedChanges = false
        selectedTool = SelectTileTool
        selectedPosition = null
    }
}
