import de.github.dudrie.hamster.external.model.HamsterGame

private fun runSampleGame(game: HamsterGame) {
    val paule = game.paule

    repeat(3) {
        paule.pickGrain()
    }
    paule.move()

    repeat(3) {
        paule.turnLeft()
    }
    repeat(2) {
        paule.move()
    }
    repeat(2) {
        paule.dropGrain()
    }
    paule.move()
}

internal fun main() {
    val game = HamsterGame("/territories/testTer01.json")
    game.startGame()

    runSampleGame(game)
}

//internal fun main() {
//    val data = InitialTerritoryData(
//        territorySize = Size(5, 7),
//        initialHamster = HamsterData(location = Location(1, 2), direction = Direction.East, grainCount = 3)
//    )
//
//    data.addWallTile(Location(0, 1))
//    data.addWallTile(Location(0, 4))
//    data.addWallTile(Location(4, 6))
//
//    data.addGrains(8, Location(3, 2))
//    data.addGrains(2, Location(1, 2))
//
//    println(data.toJson())
//
//    val json = ResourceReader("/territories/testTer01.json").getContentAsText()
//    val parsedData = InitialTerritoryData.fromJson(json)
//    println(parsedData.getAllSpecialTiles().size)
//}
