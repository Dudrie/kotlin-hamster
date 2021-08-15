import de.github.dudrie.hamster.external.model.HamsterGame

private fun runSampleGame(game: HamsterGame) {
    val paule = game.paule

    repeat(3) {
        paule.pickGrain()
    }
    paule.move()

    paule.talk("I said something :)")

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
