package de.github.dudrie.hamster.importer

import de.github.dudrie.hamster.file.model.InitialTerritoryData
import de.github.dudrie.hamster.importer.helpers.ResourceReader
import de.github.dudrie.hamster.importer.helpers.TerritoryBuilder
import de.github.dudrie.hamster.internal.model.hamster.GameHamster
import de.github.dudrie.hamster.internal.model.territory.GameTerritory

class GameImporter(private val territoryFile: String, fromOuterModule: Boolean = true) {
    /**
     * Data used to generate the [GameTerritory] and [GameHamster].
     */
    private val data: InitialTerritoryData

    lateinit var gameTerritory: GameTerritory
        private set

    lateinit var gameHamster: GameHamster
        private set

    init {
        val json = ResourceReader(getPathToTerritoryFile(), fromOuterModule).getContentAsText()
        data = InitialTerritoryData.fromJson(json)

        initTerritory()
        initHamster()
    }

    /**
     * Generates the initial [GameTerritory] from the [data].
     *
     * [data] must be loaded before calling this function.
     */
    private fun initTerritory() {
        val size = data.territorySize.copy()
        val builder = TerritoryBuilder(size, data.tileToMeterScaling)
        initSpecialTiles(builder)

        gameTerritory = builder.buildGameTerritory()
    }

    /**
     * Generates the initial [GameTerritory] from the [data] and [gameTerritory].
     *
     * [data] must be loaded and [gameTerritory] must be initialized before calling this function.
     */
    private fun initHamster() {
        val hamsterData = data.initialHamster

        gameHamster = GameHamster(
            territory = gameTerritory,
            tile = gameTerritory.getTileAt(hamsterData.location),
            direction = hamsterData.direction,
            grainCount = hamsterData.grainCount
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
     * If the path does not end with `.json` the suffix will get added.
     */
    private fun getPathToTerritoryFile(): String {
        val path = territoryFile

        return if (path.endsWith(".json")) path else "$path.json"
    }
}
