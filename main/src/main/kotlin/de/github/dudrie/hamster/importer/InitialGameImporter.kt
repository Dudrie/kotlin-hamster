package de.github.dudrie.hamster.importer

import de.github.dudrie.hamster.external.model.Hamster
import de.github.dudrie.hamster.external.model.HamsterSpiel
import de.github.dudrie.hamster.external.model.Territorium
import de.github.dudrie.hamster.importer.InitialGameImporter.Companion.DEFAULT_FILE

/**
 * Importer to import the initial game data from a corresponding file.
 *
 * @param hamsterGame Game into which the data should be imported.
 * @param territoryFile File to load the data from. If not provided the default file [DEFAULT_FILE] will be used.
 */
class InitialGameImporter(private val hamsterGame: HamsterSpiel, private val territoryFile: String? = null)     {

    /**
     * Object holding some general information
     */
    companion object {
        /**
         * Path to the default territory file which gets loaded if [territoryFile] is not specified.
         */
        private const val DEFAULT_FILE = "/territories/defaultTerritory.json"
    }

    private val importer = GameImporter(territoryFile ?: DEFAULT_FILE, territoryFile != null)

    /**
     * Generated territory.
     */
    val territory: Territorium = Territorium(hamsterGame, importer.gameTerritory)

    /**
     * Generated hamster.
     */
    val hamster: Hamster = Hamster(
        territory,
        importer.gameHamster.tile.location,
        importer.gameHamster.direction,
        importer.gameHamster.grainCount
    )
}
