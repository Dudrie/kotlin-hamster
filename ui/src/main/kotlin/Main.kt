import de.github.dudrie.hamster.internal.model.hamster.commands.PickGrainCommand
import de.github.dudrie.hamster.ui.application.HamsterGameCompose

private fun setGrains() {
    Thread.sleep(1000L)
    repeat(40) {
        val game = HamsterGameCompose.hamsterGame
        game.executeCommand(PickGrainCommand(game.hamsterGameViewModel.hamster))
        println("Picked grain ${it + 1}")
    }
}

fun main() {
    HamsterGameCompose().startGame()

    println("Hello world")
    setGrains()
}
