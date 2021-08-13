import de.github.dudrie.hamster.internal.model.hamster.commands.MoveCommand
import de.github.dudrie.hamster.internal.model.hamster.commands.PickGrainCommand
import de.github.dudrie.hamster.ui.application.HamsterGameCompose

private fun setGrains() {
    Thread.sleep(1000L)
    val game = HamsterGameCompose.hamsterGame
    repeat(40) {
        game.executeCommand(PickGrainCommand(game.hamsterGameViewModel.hamster))
        println("Picked grain ${it + 1}")
    }
    Thread.sleep(500L)
    game.executeCommand(MoveCommand(game.hamsterGameViewModel.hamster))
    println("Hamster moved")
}

fun main() {
    HamsterGameCompose().startGame()

    println("Hello world")
    setGrains()
}
