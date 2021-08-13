import de.github.dudrie.hamster.internal.model.hamster.commands.MoveCommand
import de.github.dudrie.hamster.internal.model.hamster.commands.TurnLeftCommand
import de.github.dudrie.hamster.ui.application.HamsterGameCompose

private fun setGrains() {
    val game = HamsterGameCompose.hamsterGame
    val hamster = game.hamsterGameViewModel.hamster
    println("Moving hamster")

    game.executeCommand(MoveCommand(hamster))
    repeat(3) {
        game.executeCommand(TurnLeftCommand(hamster))
    }
    repeat(2) {
        game.executeCommand(MoveCommand(hamster))
    }
}

fun main() {
    HamsterGameCompose().startGame(true)

    println("Hello world")
    setGrains()
}
