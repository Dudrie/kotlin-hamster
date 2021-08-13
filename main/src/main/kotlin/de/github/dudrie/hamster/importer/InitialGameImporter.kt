package de.github.dudrie.hamster.importer

import de.github.dudrie.hamster.importer.data.InitialTerritoryData
import de.github.dudrie.hamster.importer.helpers.ResourceReader
import de.github.dudrie.hamster.internal.model.hamster.GameHamster
import de.github.dudrie.hamster.internal.model.territory.GameTerritory

class InitialGameImporter(private val territoryFile: String? = null) {
    companion object {
        private const val DEFAULT_FILE = "/territories/defaultTerritory.json"
    }

    private val data: InitialTerritoryData

    lateinit var territory: GameTerritory
        private set

    lateinit var hamster: GameHamster
        private set

    init {
        val json = ResourceReader(getPathToTerritoryFile()).getContentAsText()
        data = InitialTerritoryData.fromJson(json)
        initTerritory()
        initHamster()
    }

    private fun initTerritory() {
        val size = data.territorySize.copy()
        val builder = TerritoryBuilder(size)
        initSpecialTiles(builder)

        territory = builder.build()
    }

    private fun initHamster() {
        val hamsterData = data.initialHamster
        val tile = territory.getTileAt(hamsterData.location)
        hamster = GameHamster(
            territory = territory,
            tile = tile,
            direction = hamsterData.direction,
            grainCount = hamsterData.grainCount
        )
    }

    private fun initSpecialTiles(builder: TerritoryBuilder) {
        data.getAllSpecialTiles().forEach { tileData -> builder.addSpecialTile(tileData) }
    }

    private fun getPathToTerritoryFile(): String {
        val path = territoryFile ?: DEFAULT_FILE

        return if (path.endsWith(".json")) path else "$path.json"
    }
}
