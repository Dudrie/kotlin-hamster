package de.github.dudrie.hamster.editor.model.builder

import de.github.dudrie.hamster.datatypes.Size
import de.github.dudrie.hamster.editor.model.EditableGameTile
import de.github.dudrie.hamster.editor.model.EditableTerritory
import de.github.dudrie.hamster.importer.helpers.TerritoryBuilder

/**
 * Builder to build an [EditableTerritory].
 */
class EditableTerritoryBuilder(territorySize: Size) : TerritoryBuilder(territorySize) {
    /**
     * Builds an [EditableTerritory] based upon the information present in the builder and returns it.
     *
     * If there are locations without a tile inside [territorySize] those will be filled with empty floor tiles before the territory gets generated.
     */
    fun buildEditableTerritory(): EditableTerritory {
        fillEmptyTiles()
        val tiles = getAllTiles().map { EditableGameTile(it.location, it.type, it.grainCount) }
        return EditableTerritory(territorySize, tiles)
    }
}
