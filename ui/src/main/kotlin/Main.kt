import de.github.dudrie.hamster.ui.application.HamsterGameCompose

private fun runSampleGame() {
    val game = HamsterGameCompose.hamsterGame
    val paule = game.hamster

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

fun main() {
    HamsterGameCompose().startGame(startPaused = false)

    runSampleGame()
}
