package de.github.dudrie.hamster.importer

import de.github.dudrie.hamster.external.model.Hamster
import de.github.dudrie.hamster.external.model.HamsterGame
import de.github.dudrie.hamster.external.model.Territory
import de.github.dudrie.hamster.importer.data.InitialTerritoryData
import de.github.dudrie.hamster.importer.helpers.ResourceReader

class InitialGameImporter(private val hamsterGame: HamsterGame, private val territoryFile: String? = null) {
    companion object {
        private const val DEFAULT_FILE = "/territories/defaultTerritory.json"
    }

    private val data: InitialTerritoryData

    lateinit var territory: Territory
        private set

    lateinit var hamster: Hamster
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

        val gameTerritory = builder.build()
        territory = Territory(hamsterGame, gameTerritory)
    }

    private fun initHamster() {
        val hamsterData = data.initialHamster
        hamster = Hamster(
            territory = territory,
            location = hamsterData.location,
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
