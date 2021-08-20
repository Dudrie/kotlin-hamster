import de.github.dudrie.hamster.external.model.HamsterGame

private fun runSampleGame(game: HamsterGame) {
    game.gameCommands.setSpeed(7f)
    val paule = game.paule

    repeat(3) {
        paule.pickGrain()
    }
    paule.move()

    repeat(5) {
        paule.say("I said something :) #$it")
    }

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
