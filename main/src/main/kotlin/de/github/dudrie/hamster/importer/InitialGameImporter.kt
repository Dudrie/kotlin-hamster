package de.github.dudrie.hamster.importer

import de.github.dudrie.hamster.external.model.Hamster
import de.github.dudrie.hamster.external.model.HamsterSpiel
import de.github.dudrie.hamster.external.model.Territorium
import de.github.dudrie.hamster.file.model.InitialTerritoryData
import de.github.dudrie.hamster.importer.InitialGameImporter.Companion.DEFAULT_FILE
import de.github.dudrie.hamster.importer.helpers.ResourceReader
import de.github.dudrie.hamster.importer.helpers.TerritoryBuilder

/**
 * Importer to import the initial game data from a corresponding file.
 *
 * @param hamsterGame Game into which the data should be imported.
 * @param territoryFile File to load the data from. If not provided the default file [DEFAULT_FILE] will be used.
 */
internal class InitialGameImporter(private val hamsterGame: HamsterSpiel, private val territoryFile: String? = null) {
    /**
     * Object holding some general information
     */
    companion object {
        /**
         * Path to the default territory file which gets loaded if [territoryFile] is not specified.
         */
        private const val DEFAULT_FILE = "/territories/defaultTerritory.json"
    }

    /**
     * Data used to generate the [Territorium] and [Hamster].
     */
    private val data: InitialTerritoryData

    /**
     * Generated territory.
     */
    lateinit var territory: Territorium
        private set

    /**
     * Generated hamster.
     */
    lateinit var hamster: Hamster
        private set

    init {
        val json = ResourceReader(getPathToTerritoryFile(), territoryFile != null).getContentAsText()
        data = InitialTerritoryData.fromJson(json)
        initTerritory()
        initHamster()
    }

    /**
     * Generates the initial [Territorium] from the [data].
     *
     * [data] must be loaded before calling this function.
     */
    private fun initTerritory() {
        val size = data.territorySize.copy()
        val builder = TerritoryBuilder(size, data.tileToMeterScaling)
        initSpecialTiles(builder)

        val gameTerritory = builder.buildGameTerritory()
        territory = Territorium(hamsterGame, gameTerritory)
    }

    /**
     * Generates the initial [Hamster] from the [data] and [territory].
     *
     * [data] must be loaded and [territory] must be initialized before calling this function.
     */
    private fun initHamster() {
        val hamsterData = data.initialHamster
        hamster = Hamster(
            territorium = territory,
            ort = hamsterData.location,
            richtung = hamsterData.direction,
            kornAnzahl = hamsterData.grainCount
        )
    }

    /**
     * Loads all special tiles into the [builder].
     */
    private fun initSpecialTiles(builder: TerritoryBuilder) {
        data.getAllSpecialTiles().forEach { tileData -> builder.addSpecialTile(tileData) }
    }

    /**
     * Helper function to return the path to the territory file.
     *
     * If [territoryFile] is not specified than the [DEFAULT_FILE] will be used. If the path does not end with `.json` the suffix will get added.
     */
    private fun getPathToTerritoryFile(): String {
        val path = territoryFile ?: DEFAULT_FILE

        return if (path.endsWith(".json")) path else "$path.json"
    }
}
