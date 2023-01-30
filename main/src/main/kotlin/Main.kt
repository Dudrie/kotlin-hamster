import de.github.dudrie.hamster.datatypes.Direction
import de.github.dudrie.hamster.datatypes.HamsterLocation
import de.github.dudrie.hamster.external.model.Hamster
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
    val game = HamsterGame("territories/testTer01.json")

    Hamster(game.territory, HamsterLocation(0, 2), Direction.South, 0)

    game.startGame()
    runSampleGame(game)
    game.stopGame()
}
