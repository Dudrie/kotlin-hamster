import de.github.dudrie.hamster.external.model.HamsterGame
import de.github.dudrie.hamster.external.model.SimpleHamsterGame

class HamsterSpiel : SimpleHamsterGame() {

    override fun run() {
        paule.move()
        paule.move()
        paule.turnLeft()
        paule.pickGrain()
        paule.move()
    }

}

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
    val game = HamsterSpiel()
    game.doRun()

//    val game = HamsterGame("territories/testTer01.json")
//
//    Hamster(game.territory, HamsterLocation(0, 2), Direction.South, 0)
//
//    game.startGame()
//    runSampleGame(game)
//    game.stopGame()
}
