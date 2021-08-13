import de.github.dudrie.hamster.datatypes.Direction
import de.github.dudrie.hamster.datatypes.Location
import de.github.dudrie.hamster.datatypes.Size
import de.github.dudrie.hamster.importer.data.HamsterData
import de.github.dudrie.hamster.importer.data.InitialTerritoryData
import de.github.dudrie.hamster.importer.helpers.ResourceReader

fun main() {
    val data = InitialTerritoryData(
        territorySize = Size(5, 7),
        initialHamster = HamsterData(location = Location(1, 2), direction = Direction.East, grainCount = 3)
    )

    data.addWallTile(Location(0, 1))
    data.addWallTile(Location(0, 4))
    data.addWallTile(Location(4, 6))

    data.addGrains(8, Location(3, 2))
    data.addGrains(2, Location(1, 2))

    println(data.toJson())

    val json = ResourceReader("/territories/testTer01.json").getContentAsText()
    val parsedData = InitialTerritoryData.fromJson(json)
    println(parsedData.getAllSpecialTiles().size)
}
